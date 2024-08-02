package com.requestapproval.requestapproval.Utils;

import com.requestapproval.requestapproval.Constants.Constants;
import com.requestapproval.requestapproval.Model.RoleDescription.RoleDescriptionRepo;
import com.requestapproval.requestapproval.Model.User.UsersRepo;
import com.requestapproval.requestapproval.Model.UserRole.UserRoleRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class BasicUtils {
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    RoleDescriptionRepo roleDescriptionRepo;
    private  int counter;
    private int counte;
    @PostConstruct
    private void init() {
        counter = usersRepo.findMaxUsrId();
    }

    @PostConstruct
    private void assigned() {
        counte = roleDescriptionRepo.findMaxRoleId();
    }

    public Boolean checkNullOrBlank(String input){
        if(Objects.isNull(input)) return true;
        input = input.trim();
        return input.equals("");
    }

    public String uniqueIdGenerate()
    {
        String prefix = Constants.UserEntity.PREFIX;
        return prefix + (++counter);

    }
    public String uniqueRoleIdGenerate()
    {
        String prefix = Constants.UserRole.PREFIX;
        return prefix + (++counte);

    }
}
