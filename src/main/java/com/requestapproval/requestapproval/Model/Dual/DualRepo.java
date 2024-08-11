package com.requestapproval.requestapproval.Model.Dual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DualRepo extends JpaRepository< DualEntity ,String > {

    @Query(value= "select role_id from role_description where name = :name " , nativeQuery = true)
    String getNameOfRole(String name);


}
