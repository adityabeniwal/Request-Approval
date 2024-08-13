package com.requestapproval.requestapproval.Service;

import com.requestapproval.requestapproval.Constants.Constants;
import com.requestapproval.requestapproval.Dto.RequestDTO.ApprovalResponseDto;
import com.requestapproval.requestapproval.Dto.RequestDTO.CreateRequestRequestDto;
import com.requestapproval.requestapproval.Dto.RequestDTO.CreateRequestResponseDto;
import com.requestapproval.requestapproval.Dto.RequestDTO.GetRequestDetailsResponseDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService
{
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

    @Transactional
    public CreateRequestResponseDto CreateRequest(CreateRequestRequestDto createRequestRequestDto)
    {
        RequestEntity requestEntity = new RequestEntity();

        requestEntity.setReqID(basicUtils.uniqueReqIdGenerate());
        requestEntity.setRevID(1);
        requestEntity.setDescription(createRequestRequestDto.getDescription());
        requestEntity.setCreator(createRequestRequestDto.getCreator());
        requestEntity.setAmount(createRequestRequestDto.getAmount());
        requestEntity.setStatus(Constants.RequestStatus.NewRequest);

        requestEntity = requestRepo.save(requestEntity);

        List<ApprovalEntity> approvalEntities = basicUtils.calculateApproval(requestEntity);

        if(approvalEntities.isEmpty())
        {
            requestEntity.setStatus(Constants.RequestStatus.Approved);
            requestRepo.save(requestEntity);
        }
        else
        {
            for (ApprovalEntity approvalEntity: approvalEntities )
            {
                approvalRepo.save(approvalEntity);

            }
        }

        CreateRequestResponseDto createRequestResponseDto = new CreateRequestResponseDto();
        createRequestResponseDto.setReqId(requestEntity.getReqID());
        createRequestResponseDto.setRevId(requestEntity.getRevID());


        return createRequestResponseDto;
    }

    public GetRequestDetailsResponseDto GetRequestDetails (int reqId,int revId) throws Exception
    {
        RequestEntity requestEntity = requestRepo.findByReqIDAndRevID(reqId,revId);

        if (requestEntity == null) {
            logger.error("Request with Request ID" + reqId + " and Revision ID " + revId + " not found");
            throw new DataNotFoundException("Request with Request ID" + reqId + " and Revision ID " + revId + " not found");
        }
        logger.info("Request found: {}", requestEntity);
        GetRequestDetailsResponseDto getRequestDetailsResponseDto = modelMapper.map(requestEntity,GetRequestDetailsResponseDto.class);

        List<ApprovalEntity> approvalEntities = approvalRepo.findAllByReqRevID(requestEntity.getReqRevID());
        if(!approvalEntities.isEmpty())
        {
            getRequestDetailsResponseDto.setApprovals(approvalEntities.stream()
                    .map(approval -> modelMapper.map(approval, ApprovalResponseDto.class))
                    .collect(Collectors.toList()));
        }

        return getRequestDetailsResponseDto;
    }
}
