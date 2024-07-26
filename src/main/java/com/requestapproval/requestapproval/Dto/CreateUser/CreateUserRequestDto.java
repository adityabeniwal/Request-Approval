package com.requestapproval.requestapproval.Dto.CreateUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateUserRequestDto {
    @JsonProperty("name")
    String name;
    @JsonProperty("email")
    String email;
    @JsonProperty("phone")
    String phone;
    @JsonProperty("DOB")
    String DOB;
    @JsonProperty("role")
    String role;
}
