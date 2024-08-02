package com.requestapproval.requestapproval.Model.RoleDescription;

import com.requestapproval.requestapproval.Model.User.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDescriptionRepo extends JpaRepository<RoleDescriptionEntity,String> {
    @Query(value = "SELECT COALESCE(MAX(CAST(SUBSTRING(role_id, 4) AS UNSIGNED)), 0) FROM role_description", nativeQuery = true)
    int findMaxRoleId();
}
