package com.requestapproval.requestapproval.Dto.RoleDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateRoleRequestDto {
    @JsonProperty("roleDescription")
    String roleDescription;

    @JsonProperty("name")
    String name;
}
