package com.requestapproval.requestapproval.Model.UserRole;

import com.requestapproval.requestapproval.Model.User.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRoleEntity,String> {


}
