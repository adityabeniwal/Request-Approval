package com.requestapproval.requestapproval.Controller;


import com.requestapproval.requestapproval.Dto.RoleDTO.RoleDescriptionResponseDto;
import com.requestapproval.requestapproval.Exception.DataNotFoundException;
import com.requestapproval.requestapproval.Model.RoleDescription.RoleDescriptionEntity;
import com.requestapproval.requestapproval.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/{roleId}")
    public RoleDescriptionResponseDto getRoleByRoleId(@PathVariable String roleId) {
        return roleService.getRoleByRoleId(roleId);
    }

    @PutMapping("/{roleId}")
    public RoleDescriptionResponseDto updateRoleDetails(@PathVariable String roleId, @RequestBody RoleDescriptionEntity roleDetails) {
        return roleService.updateRoleDetails(roleId, roleDetails);
    }
}
