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

    private RequestEntity findRequestEntity(int reqId, int revId) {
        RequestEntity requestEntity = requestRepo.findByReqIDAndRevID(reqId, revId);
        if (requestEntity == null) {
            String errorMessage = String.format("Request with Request ID %d and Revision ID %d not found", reqId, revId);
            logger.error(errorMessage);
            throw new DataNotFoundException(errorMessage);
        }
        return requestEntity;
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

    @Transactional
    public SubmitRequestResponseDto SubmitRequest(int reqId, int revId, SubmitRequestRequestDto submitRequestRequestDto) {
        RequestEntity requestEntity = findRequestEntity(reqId, revId);

        logger.info("Request found: {}", requestEntity);

        boolean isDescriptionChanged = !Objects.equals(requestEntity.getDescription(), submitRequestRequestDto.getDescription());
        boolean isAmountChanged = requestEntity.getAmount() != submitRequestRequestDto.getAmount();

        if (isDescriptionChanged || isAmountChanged)
        {
            requestEntity.setDescription(submitRequestRequestDto.getDescription());
            requestEntity.setAmount(submitRequestRequestDto.getAmount());

            // Remove old approval entities
            List<ApprovalEntity> oldApprovalEntities = approvalRepo.findAllByReqRevID(requestEntity.getReqRevID());
            if (!oldApprovalEntities.isEmpty()) {
                approvalRepo.deleteAll(oldApprovalEntities);
            }

            // Calculate and handle new approval entities
            List<ApprovalEntity> newApprovalEntities = basicUtils.calculateApproval(requestEntity);
            if (newApprovalEntities.isEmpty()) {
                requestEntity.setStatus(Constants.RequestStatus.Approved);
            } else {
                newApprovalEntities.forEach(approvalEntity -> {
                    approvalEntity.setApprovalStatus(Constants.ApprovalStatus.Pending);
                    approvalRepo.save(approvalEntity);
                });
                requestEntity.setStatus(Constants.RequestStatus.Pending);
            }

        }
        else
        {
            List<ApprovalEntity> oldApprovalEntities = approvalRepo.findAllByReqRevID(requestEntity.getReqRevID());
            if (!oldApprovalEntities.isEmpty()) {
                oldApprovalEntities.forEach(approvalEntity -> {
                    approvalEntity.setApprovalStatus(Constants.ApprovalStatus.Pending);
                    approvalRepo.save(approvalEntity);
                });
            }

            requestEntity.setStatus(Constants.RequestStatus.Pending);
        }

        requestRepo.save(requestEntity);

        return modelMapper.map(requestEntity,SubmitRequestResponseDto.class);

    }

    @Transactional
    public RevokeRequestResponseDto RevokeRequest(int reqId, int revId)
    {
        RequestEntity requestEntity = findRequestEntity(reqId, revId);

        logger.info("Request found: {}", requestEntity);

        requestEntity.setStatus(Constants.RequestStatus.Revoked);
        requestRepo.save(requestEntity);

        return modelMapper.map(requestEntity,RevokeRequestResponseDto.class);
    }

    public NewRevisionResponseDto NewRevision(int reqId, int revId)
    {
        RequestEntity oldRequestEntity = findRequestEntity(reqId, revId);

        logger.info("Request found: {}", oldRequestEntity);

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

        ApprovalEntity approvalEntity = approvalRepo.findByReqRevIDAndRoleId(requestEntity.getReqRevID(),approveRequestRequestDto.getRoleId());

        if(approvalEntity == null)
        {
            throw new DataNotFoundException("No Approval found for the given Role");
        }
        if (Objects.equals(approvalEntity.getApprovalStatus(), Constants.ApprovalStatus.Approve))
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Already approved for given role");
        }

        approvalEntity.setApprovalStatus(Constants.ApprovalStatus.Approve);
        approvalEntity.setComment(approveRequestRequestDto.getComment());

        approvalRepo.save(approvalEntity);

        return modelMapper.map(approvalEntity,ApproveRequestResponseDto.class);
    }
}
