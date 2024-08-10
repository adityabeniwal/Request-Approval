package com.requestapproval.requestapproval.Utils;

import com.requestapproval.requestapproval.Constants.Constants;
import com.requestapproval.requestapproval.Model.Request.RequestRepo;
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
    @Autowired
    RequestRepo requestRepo;
    private  int maxUsrId;
    private int maxRoleId;
    private int maxReqId;

    @PostConstruct
    private void init() {
        maxUsrId = usersRepo.findMaxUsrId();
        maxRoleId = roleDescriptionRepo.findMaxRoleId();
        maxReqId = requestRepo.findMaxReqId();
    }

    public Boolean checkNullOrBlank(String input){
        if(Objects.isNull(input)) return true;
        input = input.trim();
        return input.equals("");
    }

    public String uniqueUsrIdGenerate()
    {
        String prefix = Constants.UserEntity.PREFIX;
        return prefix + (++maxUsrId);

    }
    public String uniqueRoleIdGenerate()
    {
        String prefix = Constants.UserRole.PREFIX;
        return prefix + (++maxRoleId);

    }

    public int uniqueReqIdGenerate()
    {
        return ++maxReqId;
    }
}
