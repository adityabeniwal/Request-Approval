package com.requestapproval.requestapproval.Service;

import com.requestapproval.requestapproval.Dto.RoleDTO.CreateRoleRequestDto;
import com.requestapproval.requestapproval.Dto.RoleDTO.RoleDescriptionResponseDto;
import com.requestapproval.requestapproval.Exception.DataNotFoundException;
import com.requestapproval.requestapproval.Model.RoleDescription.RoleDescriptionEntity;
import com.requestapproval.requestapproval.Model.RoleDescription.RoleDescriptionRepo;
import com.requestapproval.requestapproval.Utils.BasicUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;


@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    BasicUtils basicUtils;
    @Autowired
    RoleDescriptionRepo roleDescriptionRepo;
    @Autowired
    private ModelMapper modelMapper;

    //Create a new Role
    public String createRole(CreateRoleRequestDto createRoleRequestDto)
    {
        RoleDescriptionEntity roleDescriptionEntity = new RoleDescriptionEntity();
        roleDescriptionEntity.setRoleId(basicUtils.uniqueRoleIdGenerate());
        roleDescriptionEntity.setRoleDescription(createRoleRequestDto.getDescription());
        roleDescriptionEntity.setName(createRoleRequestDto.getName());
        roleDescriptionRepo.save(roleDescriptionEntity);
        return " Role_id = " +  roleDescriptionEntity.getRoleId();
    }

    //Get Role Details by Role ID
    public RoleDescriptionResponseDto getRoleByRoleId(String roleId) {

        RoleDescriptionEntity roleDescriptionEntity = roleDescriptionRepo.findRoleDescriptionAndNameByRoleId(roleId);
        if (roleDescriptionEntity == null) {
            logger.error("Role with ID {} not found", roleId);
            throw new DataNotFoundException("Role with ID " + roleId + " not found");
        }
        logger.info("Role found: {}", roleDescriptionEntity);

        return modelMapper.map(roleDescriptionEntity, RoleDescriptionResponseDto.class);
    }

    //Update the role Details
   public RoleDescriptionResponseDto updateRoleDetails(String roleId, RoleDescriptionEntity updatedRoleDetails) {
        Optional<RoleDescriptionEntity> oldDetails= roleDescriptionRepo.findById(roleId);
        if(oldDetails.isPresent()) {
            RoleDescriptionEntity roleDetails= oldDetails.get();
            if(updatedRoleDetails.getRoleDescription()!=null){
                roleDetails.setRoleDescription(updatedRoleDetails.getRoleDescription());
            }
            if(updatedRoleDetails.getName()!=null){
                roleDetails.setName(updatedRoleDetails.getName());
            }
            RoleDescriptionEntity savedDetails=  roleDescriptionRepo.save(roleDetails);
            return modelMapper.map(savedDetails, RoleDescriptionResponseDto.class);
        }
        else throw new DataNotFoundException("Role Id not found:" +roleId);
   }

}
