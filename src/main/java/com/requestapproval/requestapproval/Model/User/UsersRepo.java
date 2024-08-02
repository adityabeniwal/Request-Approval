package com.requestapproval.requestapproval.Model.User;

import com.requestapproval.requestapproval.Model.User.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<UsersEntity,String>
{

    @Query(value = "SELECT COALESCE(MAX(CAST(SUBSTRING(usr_id, 5) AS UNSIGNED)), 0) FROM users", nativeQuery = true)
    int findMaxUsrId();
}
