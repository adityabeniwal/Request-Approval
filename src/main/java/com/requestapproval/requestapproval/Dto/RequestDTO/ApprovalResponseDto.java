package com.requestapproval.requestapproval.Dto.RequestDTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ApprovalResponseDto
{

    private String roleId;

    private String approvalStatus;

    private String comment;

}
