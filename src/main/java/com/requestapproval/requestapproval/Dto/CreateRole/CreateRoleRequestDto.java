package com.requestapproval.requestapproval.Dto.CreateRole;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateRoleRequestDto {
    @JsonProperty("Description")
    String Description;
    @JsonProperty("name")
    String name;
}
