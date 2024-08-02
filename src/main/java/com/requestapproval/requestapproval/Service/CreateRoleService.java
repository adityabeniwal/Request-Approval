package com.requestapproval.requestapproval.Service;

import com.requestapproval.requestapproval.Dto.CreateRole.CreateRoleRequestDto;
import com.requestapproval.requestapproval.Model.RoleDescription.RoleDescriptionEntity;
import com.requestapproval.requestapproval.Model.RoleDescription.RoleDescriptionRepo;
import com.requestapproval.requestapproval.Utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CreateRoleService {
//    @Autowired
//    RoleDescriptionEntity roleDescriptionEntity;
    @Autowired
    BasicUtils basicUtils;
    @Autowired
    RoleDescriptionRepo roleDescriptionRepo;
    public String createRole(CreateRoleRequestDto createRoleRequestDto)
    {
        RoleDescriptionEntity roleDescriptionEntity = new RoleDescriptionEntity();
        roleDescriptionEntity.setRoleId(basicUtils.uniqueRoleIdGenerate());
        roleDescriptionEntity.setRoleDescription(createRoleRequestDto.getDescription());
        roleDescriptionEntity.setName(createRoleRequestDto.getName());
        roleDescriptionRepo.save(roleDescriptionEntity);
        return " Role_id = " +  roleDescriptionEntity.getRoleId();
    }

}
