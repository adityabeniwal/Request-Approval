package com.requestapproval.requestapproval.Controller;


import com.requestapproval.requestapproval.Dto.RoleDTO.RoleDescriptionRequestDto;
import com.requestapproval.requestapproval.Dto.RoleDTO.CreateRoleRequestDto;
import com.requestapproval.requestapproval.Dto.RoleDTO.RoleDescriptionResponseDto;
import com.requestapproval.requestapproval.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(path = "/createRole")
    public String createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto) throws Exception
    {
        return roleService.createRole(createRoleRequestDto);
    }

    @GetMapping("/{roleId}")
    public RoleDescriptionResponseDto getRoleByRoleId(@PathVariable String roleId) {
        return roleService.getRoleByRoleId(roleId);
    }

    @PutMapping("/{roleId}")
    public RoleDescriptionResponseDto updateRoleDetails(@PathVariable String roleId, @RequestBody RoleDescriptionRequestDto roleDetails) {
        return roleService.updateRoleDetails(roleId, roleDetails);
    }


}
