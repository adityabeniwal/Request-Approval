package com.requestapproval.requestapproval.Dto.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubmitRequestRequestDto
{
    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private int amount;
}
