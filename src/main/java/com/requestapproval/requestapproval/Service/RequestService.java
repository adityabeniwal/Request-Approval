package com.requestapproval.requestapproval.Service;

import com.requestapproval.requestapproval.Constants.Constants;
import com.requestapproval.requestapproval.Dto.Request.CreateRequestRequestDto;
import com.requestapproval.requestapproval.Dto.Request.CreateRequestResponseDto;
import com.requestapproval.requestapproval.Model.Approval.ApprovalEntity;
import com.requestapproval.requestapproval.Model.Approval.ApprovalRepo;
import com.requestapproval.requestapproval.Model.Request.RequestEntity;
import com.requestapproval.requestapproval.Model.Request.RequestRepo;
import com.requestapproval.requestapproval.Utils.BasicUtils;
import com.requestapproval.requestapproval.Utils.RaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestService
{
    @Autowired
    BasicUtils basicUtils;

    @Autowired
    RaUtils raUtils;

    @Autowired
    RequestRepo requestRepo;
    @Autowired
    ApprovalRepo approvalRepo;

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
}
