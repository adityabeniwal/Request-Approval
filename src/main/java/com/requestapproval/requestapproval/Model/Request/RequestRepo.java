package com.requestapproval.requestapproval.Model.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<RequestEntity,Integer>
{
    @Query(value = "SELECT coalesce(max(req_id),0) AS req_id FROM requests;", nativeQuery = true)
    int findMaxReqId();
}
