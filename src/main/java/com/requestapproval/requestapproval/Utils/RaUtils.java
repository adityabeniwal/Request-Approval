package com.requestapproval.requestapproval.Utils;

import com.requestapproval.requestapproval.Constants.Constants;
import com.requestapproval.requestapproval.Dto.Request.CreateRequestRequestDto;
import com.requestapproval.requestapproval.Model.Approval.ApprovalEntity;
import com.requestapproval.requestapproval.Model.Request.RequestEntity;
import com.requestapproval.requestapproval.Model.RoleDescription.RoleDescriptionEntity;
import com.requestapproval.requestapproval.Model.RoleDescription.RoleDescriptionRepo;
import com.requestapproval.requestapproval.Model.User.UsersEntity;
import com.requestapproval.requestapproval.Model.User.UsersRepo;
import com.requestapproval.requestapproval.Model.UserRole.UserRoleEntity;
import com.requestapproval.requestapproval.Model.UserRole.UserRoleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
