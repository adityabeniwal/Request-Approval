package com.requestapproval.requestapproval.Utils;

import com.requestapproval.requestapproval.Model.User.UsersEntity;
import com.requestapproval.requestapproval.Model.User.UsersRepo;
import com.requestapproval.requestapproval.Model.Role.UserRoleEntity;
import com.requestapproval.requestapproval.Model.Role.UserRoleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaUtils {
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    UserRoleRepo userRoleRepo;
    @Transactional
    public void saveUsersndUserRoleToDb(UsersEntity usersEntity , UserRoleEntity userRoleEntity)
    {
        usersRepo.save(usersEntity);
        userRoleEntity.setUsrId(usersEntity.getUsrId());
        userRoleRepo.save(userRoleEntity);
    }



}
