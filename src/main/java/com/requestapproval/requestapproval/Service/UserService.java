package com.requestapproval.requestapproval.Service;

import com.requestapproval.requestapproval.Constants.Constants;
import com.requestapproval.requestapproval.Dto.UserDTO.CreateUserRequestDto;
import com.requestapproval.requestapproval.Model.Dual.DualRepo;
import com.requestapproval.requestapproval.Model.User.UsersEntity;
import com.requestapproval.requestapproval.Model.User.UsersRepo;
import com.requestapproval.requestapproval.Model.Role.UserRoleEntity;
import com.requestapproval.requestapproval.Utils.BasicUtils;

import com.requestapproval.requestapproval.Utils.RaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    BasicUtils basicUtils;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    DualRepo dualRepo;
    @Autowired
    RaUtils raUtils;

    public String CreateUser(CreateUserRequestDto createUserRequestDto) {
        UsersEntity usersEntity = new UsersEntity();
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        //userEntity data saved
            usersEntity.setUsrId(basicUtils.uniqueIdGenerate());
            if(basicUtils.checkNullOrBlank(createUserRequestDto.getName())) {
                return "Name can't be  null";
            }
            else usersEntity.setName(createUserRequestDto.getName());
            usersEntity.setEmail(createUserRequestDto.getEmail());
            usersEntity.setDOB(createUserRequestDto.getDOB());
            usersEntity.setPhone(createUserRequestDto.getPhone());
            usersEntity.setStatus(Constants.UserEntity.STATUS);

            //UserRole data saved
       // userRoleEntity.setUsrId(basicUtils.uniqueIdGenerate());
        userRoleEntity.setRoleId(dualRepo.getNameOfRole(createUserRequestDto.getRole()));
        userRoleEntity.setStatus(Constants.UserEntity.STATUS);

        //save data in table
        raUtils.saveUsersndUserRoleToDb(usersEntity,userRoleEntity);

            return " User_id = " +  usersEntity.getUsrId();
    }




}
