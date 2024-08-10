package com.requestapproval.requestapproval.Controller;

import com.requestapproval.requestapproval.Dto.RoleDTO.CreateRoleRequestDto;
import com.requestapproval.requestapproval.Dto.UserDTO.CreateUserRequestDto;
import com.requestapproval.requestapproval.Service.RoleService;
import com.requestapproval.requestapproval.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserService userService;
    @Autowired
    RoleService createRoleService;

    @PostMapping(path = "/createUser")
    public String createUser(@RequestBody CreateUserRequestDto createUserRequestDto) throws Exception
    {
        String response = userService.CreateUser(createUserRequestDto);
        return response;

    }
    @PostMapping(path = "/createRole")
    public String createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto) throws Exception
    {
        String response = createRoleService.createRole(createRoleRequestDto);
        return response;
    }

}
