package com.requestapproval.requestapproval.Dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateRequestRequestDto
{
    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private int amount;

    @JsonProperty("creator")
    private String creator;
}
