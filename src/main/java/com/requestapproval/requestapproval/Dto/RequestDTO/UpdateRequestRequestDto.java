package com.requestapproval.requestapproval.Dto.RequestDTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UpdateRequestRequestDto
{
    private String description;

    private int amount;
}
