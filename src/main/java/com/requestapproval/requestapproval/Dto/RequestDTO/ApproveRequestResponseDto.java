package com.requestapproval.requestapproval.Dto.RequestDTO;

import lombok.Data;

@Data
public class ApproveRequestResponseDto
{
    private String roleId;

    private String approvalStatus;

    private String comment;
}
