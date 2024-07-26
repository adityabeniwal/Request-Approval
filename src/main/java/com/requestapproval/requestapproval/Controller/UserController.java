package com.requestapproval.requestapproval.Controller;

import com.requestapproval.requestapproval.Dto.CreateUser.CreateUserRequestDto;
import com.requestapproval.requestapproval.Model.User.UsersEntity;
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

    @PostMapping(path = "/createUser")
    public String createUser(@RequestBody CreateUserRequestDto createUserRequestDto) throws Exception
    {
        String response = createUserService.CreateUser(createUserRequestDto);
        return response;

    }

}
