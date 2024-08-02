package com.requestapproval.requestapproval.Controller;

import com.requestapproval.requestapproval.Dto.CreateRole.CreateRoleRequestDto;
import com.requestapproval.requestapproval.Dto.CreateUser.CreateUserRequestDto;
import com.requestapproval.requestapproval.Service.CreateRoleService;
import com.requestapproval.requestapproval.Service.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    CreateUserService createUserService;
    @Autowired
    CreateRoleService createRoleService;

    @PostMapping(path = "/createUser")
    public String createUser(@RequestBody CreateUserRequestDto createUserRequestDto) throws Exception
    {
        String response = createUserService.CreateUser(createUserRequestDto);
        return response;

    }
    @PostMapping(path = "/createRole")
    public String createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto) throws Exception
    {
        String response = createRoleService.createRole(createRoleRequestDto);
        return response;
    }

}
