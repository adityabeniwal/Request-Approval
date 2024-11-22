package com.requestapproval.requestapproval.Dto.RequestDTO;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class UpdateRequestResponseDto
{
    private int reqID;
    private int revID;
    private String description;
    private int amount;
    private String status;
}
