package com.requestapproval.requestapproval.Dto.RequestDTO;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class GetRequestDetailsResponseDto
{

    private int reqID;

    private int revID;

    private String description;

    private int amount;

    private String status;

    private String creator;

    List<ApprovalResponseDto> approvals;


}

