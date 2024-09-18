package com.requestapproval.requestapproval.Dto.RequestDTO;

import lombok.Data;

@Data
public class DeclineRequestResponseDto
{
    private String roleId;

    private String approvalStatus;

    private String comment;
}
