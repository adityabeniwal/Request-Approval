package com.requestapproval.requestapproval.Service;

import com.requestapproval.requestapproval.Dto.CreateUser.CreateUserRequestDto;
import com.requestapproval.requestapproval.Model.User.UsersEntity;
import com.requestapproval.requestapproval.Utils.BasicUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    @Autowired
    BasicUtils basicUtils;
    public String CreateUser(CreateUserRequestDto createUserRequestDto) {

            if(!basicUtils.checkNullOrBlank(createUserRequestDto.getName()))
            {
                UsersEntity usersEntity = new UsersEntity();
                usersEntity.setName(createUserRequestDto.getName());
            }

        return " ";
    }
}
