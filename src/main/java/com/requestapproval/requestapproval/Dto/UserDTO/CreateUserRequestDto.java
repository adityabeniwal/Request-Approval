package com.requestapproval.requestapproval.Dto.UserDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CreateUserRequestDto {
    @JsonProperty("name")
    String name;

    @JsonProperty("email")
    String email;

    @JsonProperty("phone")
    String phone;

    @JsonProperty("DOB")
    Date DOB;

    @JsonProperty("role")
    String role;
}
