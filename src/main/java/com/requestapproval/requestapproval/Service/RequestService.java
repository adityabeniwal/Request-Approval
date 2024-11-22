package com.requestapproval.requestapproval.Service;

import com.requestapproval.requestapproval.Constants.Constants;
import com.requestapproval.requestapproval.Dto.RequestDTO.*;
import com.requestapproval.requestapproval.Dto.RoleDTO.RoleDescriptionResponseDto;
import com.requestapproval.requestapproval.Exception.DataNotFoundException;
import com.requestapproval.requestapproval.Model.Approval.ApprovalEntity;
import com.requestapproval.requestapproval.Model.Approval.ApprovalRepo;
import com.requestapproval.requestapproval.Model.Request.RequestEntity;
import com.requestapproval.requestapproval.Model.Request.RequestRepo;
import com.requestapproval.requestapproval.Utils.BasicUtils;
import com.requestapproval.requestapproval.Utils.RaUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    BasicUtils basicUtils;

    @Autowired
    RaUtils raUtils;

    @Autowired
    RequestRepo requestRepo;
    @Autowired
    ApprovalRepo approvalRepo;
    @Autowired
    private ModelMapper modelMapper;

    public RequestEntity findRequestEntity(int reqId, int revId) {
        RequestEntity requestEntity = requestRepo.findByReqIDAndRevID(reqId, revId);
        if (requestEntity == null) {
            String errorMessage = String.format("Request with Request ID %d and Revision ID %d not found", reqId, revId);
            logger.error(errorMessage);
            throw new DataNotFoundException(errorMessage);
        }
        return requestEntity;
    }


    @Transactional
    public RequestEntity updateRequestData(int reqId,int revId, RequestEntity newRequestEntity)
    {
        RequestEntity oldRequestEntity = findRequestEntity(reqId,revId);

        if (!oldRequestEntity.getDescription().equals(newRequestEntity.getDescription()) || oldRequestEntity.getAmount() != newRequestEntity.getAmount())
        {
            oldRequestEntity.setDescription(newRequestEntity.getDescription());
            oldRequestEntity.setAmount(newRequestEntity.getAmount());

            // Remove old approval entities
            List<ApprovalEntity> oldApprovalEntities = approvalRepo.findAllByReqRevID(oldRequestEntity.getReqRevID());
            if (!oldApprovalEntities.isEmpty()) {
                approvalRepo.deleteAll(oldApprovalEntities);
            }

            // Calculate and handle new approval entities
            List<ApprovalEntity> newApprovalEntities = basicUtils.calculateApproval(oldRequestEntity);
            if (!newApprovalEntities.isEmpty())
            {
                newApprovalEntities.forEach(approvalEntity -> {
                    approvalEntity.setApprovalStatus(Constants.ApprovalStatus.New);
                    approvalRepo.save(approvalEntity);
                });
            }

            requestRepo.save(oldRequestEntity);
        }
        return oldRequestEntity;

    }

    @Transactional
    public CreateRequestResponseDto CreateRequest(CreateRequestRequestDto createRequestRequestDto) {
        RequestEntity requestEntity = modelMapper.map(createRequestRequestDto,RequestEntity.class);

        requestEntity.setReqID(basicUtils.uniqueReqIdGenerate());
        requestEntity.setRevID(1);
        requestEntity.setStatus(Constants.RequestStatus.NewRequest);

        requestRepo.save(requestEntity);

        List<ApprovalEntity> approvalEntities = basicUtils.calculateApproval(requestEntity);

        if (approvalEntities.isEmpty()) {
            requestEntity.setStatus(Constants.RequestStatus.Approved);
            requestRepo.save(requestEntity);
        } else {
            approvalRepo.saveAll(approvalEntities);
        }

        return modelMapper.map(requestEntity,CreateRequestResponseDto.class);
    }

    public GetRequestDetailsResponseDto GetRequestDetails(int reqId, int revId) throws Exception {
        RequestEntity requestEntity = findRequestEntity(reqId, revId);

        logger.info("Request found: {}", requestEntity);
        GetRequestDetailsResponseDto getRequestDetailsResponseDto = modelMapper.map(requestEntity, GetRequestDetailsResponseDto.class);

        List<ApprovalEntity> approvalEntities = approvalRepo.findAllByReqRevID(requestEntity.getReqRevID());
        if (!approvalEntities.isEmpty()) {
            getRequestDetailsResponseDto.setApprovals(approvalEntities.stream()
                    .map(approval -> modelMapper.map(approval, ApprovalResponseDto.class))
                    .collect(Collectors.toList()));
        }

        return getRequestDetailsResponseDto;
    }

    public UpdateRequestResponseDto UpdateRequest(int reqId,int revId,UpdateRequestRequestDto updateRequestRequestDto)
    {
        RequestEntity requestEntity = findRequestEntity(reqId, revId);
        logger.info("Request found: {}", requestEntity);

        if(revId<requestRepo.findMaxRevIdByReqId(reqId) || !(Objects.equals(requestEntity.getStatus(), Constants.RequestStatus.NewRequest) || Objects.equals(requestEntity.getStatus(), Constants.RequestStatus.NewRevision)))
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Update is only allowed for either New Request or latest New revision ");
        }
        RequestEntity updatedRequestEntity = updateRequestData(reqId,revId,modelMapper.map(updateRequestRequestDto,RequestEntity.class));

        return modelMapper.map(updatedRequestEntity,UpdateRequestResponseDto.class);
    }

    @Transactional
    public SubmitRequestResponseDto SubmitRequest(int reqId, int revId, SubmitRequestRequestDto submitRequestRequestDto) {
        RequestEntity requestEntity = findRequestEntity(reqId, revId);
        logger.info("Request found: {}", requestEntity);

        if(revId<requestRepo.findMaxRevIdByReqId(reqId) || !(Objects.equals(requestEntity.getStatus(), Constants.RequestStatus.NewRequest) || Objects.equals(requestEntity.getStatus(), Constants.RequestStatus.NewRevision)))
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Submission is only allowed for either New Request or latest New revision ");
        }

        RequestEntity updatedRequestEntity = updateRequestData(reqId,revId,modelMapper.map(submitRequestRequestDto,RequestEntity.class));

        List<ApprovalEntity> approvalEntities = approvalRepo.findAllByReqRevID(updatedRequestEntity.getReqRevID());
        if (approvalEntities.isEmpty())
        {
            updatedRequestEntity.setStatus(Constants.RequestStatus.Approved);
        }
        else {
            approvalEntities.forEach(approvalEntity -> {
                approvalEntity.setApprovalStatus(Constants.ApprovalStatus.Pending);
                approvalRepo.save(approvalEntity);
            });
            updatedRequestEntity.setStatus(Constants.RequestStatus.Pending);
        }
        requestRepo.save(updatedRequestEntity);

        return modelMapper.map(updatedRequestEntity,SubmitRequestResponseDto.class);

    }

    @Transactional
    public RevokeRequestResponseDto RevokeRequest(int reqId, int revId)
    {
        RequestEntity requestEntity = findRequestEntity(reqId, revId);

        logger.info("Request found: {}", requestEntity);

        if(revId<requestRepo.findMaxRevIdByReqId(reqId))
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Revoke is only allowed for latest revision");
        }

        if(Objects.equals(requestEntity.getStatus(), Constants.RequestStatus.Revoked) || Objects.equals(requestEntity.getStatus(), Constants.RequestStatus.Approved) || Objects.equals(requestEntity.getStatus(), Constants.RequestStatus.Declined))
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Request can't be revoked when already in " + requestEntity.getStatus() + " status");
        }
        requestEntity.setStatus(Constants.RequestStatus.Revoked);
        requestRepo.save(requestEntity);

        return modelMapper.map(requestEntity,RevokeRequestResponseDto.class);
    }

    public NewRevisionResponseDto NewRevision(int reqId, int revId)
    {
        RequestEntity oldRequestEntity = findRequestEntity(reqId, revId);

        logger.info("Request found: {}", oldRequestEntity);

        if(revId<requestRepo.findMaxRevIdByReqId(reqId))
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "New Revision can only be created for latest revision");
        }

        if(Objects.equals(oldRequestEntity.getStatus(), Constants.RequestStatus.NewRevision) || Objects.equals(oldRequestEntity.getStatus(), Constants.RequestStatus.NewRequest) || Objects.equals(oldRequestEntity.getStatus(), Constants.RequestStatus.Pending))
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "New Revision is not allowed when request is in " + oldRequestEntity.getStatus() + " status" );
        }

        RequestEntity newRequestEntity = modelMapper.typeMap(RequestEntity.class,RequestEntity.class).addMappings(mapper -> mapper.skip(RequestEntity::setReqRevID)).map(oldRequestEntity);

        newRequestEntity.setRevID(oldRequestEntity.getRevID()+1);
        newRequestEntity.setStatus(Constants.RequestStatus.NewRevision);

        requestRepo.save(newRequestEntity);

        List<ApprovalEntity> approvalEntities = basicUtils.calculateApproval(newRequestEntity);

        if (approvalEntities.isEmpty()) {
            newRequestEntity.setStatus(Constants.RequestStatus.Approved);
            requestRepo.save(newRequestEntity);
        } else {
            approvalRepo.saveAll(approvalEntities);
        }

        return modelMapper.map(newRequestEntity, NewRevisionResponseDto.class);
    }

    public ApproveRequestResponseDto ApproveRequest (int reqId,int revId,ApproveRequestRequestDto approveRequestRequestDto) throws Exception
    {
        RequestEntity requestEntity = findRequestEntity(reqId, revId);
        logger.info("Request found: {}", requestEntity);

        if(revId<requestRepo.findMaxRevIdByReqId(reqId) || !Objects.equals(requestEntity.getStatus(), Constants.RequestStatus.Pending))
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Approval can be triggered for latest submitted revision only");
        }

        ApprovalEntity approvalEntity = approvalRepo.findByReqRevIDAndRoleId(requestEntity.getReqRevID(),approveRequestRequestDto.getRoleId());

        if(approvalEntity == null)
        {
            throw new DataNotFoundException("No Approval found for the given Role");
        }
        if (Objects.equals(approvalEntity.getApprovalStatus(), Constants.ApprovalStatus.Approve) || Objects.equals(approvalEntity.getApprovalStatus(), Constants.ApprovalStatus.Decline) )
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Requested approval is already in " + approvalEntity.getApprovalStatus() + " status" );
        }

        approvalEntity.setApprovalStatus(Constants.ApprovalStatus.Approve);
        approvalEntity.setComment(approveRequestRequestDto.getComment());

        approvalRepo.save(approvalEntity);

        List<ApprovalEntity> approvalEntities = approvalRepo.findAllByReqRevID(requestEntity.getReqRevID());

        boolean isAllApproved = approvalEntities.stream()
            .allMatch(approval -> Objects.equals(approval.getApprovalStatus(), Constants.ApprovalStatus.Approve));


        if(isAllApproved)
        {
            requestEntity.setStatus(Constants.RequestStatus.Approved);
            requestRepo.save(requestEntity);
        }

        return modelMapper.map(approvalEntity,ApproveRequestResponseDto.class);
    }

    public DeclineRequestResponseDto DeclineRequest (int reqId,int revId,DeclineRequestRequestDto declineRequestRequestDto) throws Exception
    {
        RequestEntity requestEntity = findRequestEntity(reqId, revId);
        logger.info("Request found: {}", requestEntity);

        if(revId<requestRepo.findMaxRevIdByReqId(reqId) || !Objects.equals(requestEntity.getStatus(), Constants.RequestStatus.Pending))
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Decline can be triggered for latest submitted revision only");
        }

        ApprovalEntity approvalEntity = approvalRepo.findByReqRevIDAndRoleId(requestEntity.getReqRevID(),declineRequestRequestDto.getRoleId());

        if(approvalEntity == null)
        {
            throw new DataNotFoundException("No Approval found for the given Role");
        }
        if (Objects.equals(approvalEntity.getApprovalStatus(), Constants.ApprovalStatus.Approve) || Objects.equals(approvalEntity.getApprovalStatus(), Constants.ApprovalStatus.Decline) )
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Requested approval is already in " + approvalEntity.getApprovalStatus() + " status" );
        }

        approvalEntity.setApprovalStatus(Constants.ApprovalStatus.Decline);
        approvalEntity.setComment(declineRequestRequestDto.getComment());

        approvalRepo.save(approvalEntity);

        requestEntity.setStatus(Constants.RequestStatus.Declined);
        requestRepo.save(requestEntity);

        return modelMapper.map(approvalEntity,DeclineRequestResponseDto.class);
    }
}
