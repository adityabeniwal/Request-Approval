package com.requestapproval.requestapproval.Dto.RoleDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateRoleRequestDto {
    @JsonProperty("Description")
    String Description;

    @JsonProperty("name")
    String name;
}
