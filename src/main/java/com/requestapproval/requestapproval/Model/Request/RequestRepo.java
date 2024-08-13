package com.requestapproval.requestapproval.Model.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<RequestEntity,Integer>
{
    @Query(value = "SELECT max(req_id) FROM requestapproval.requests;", nativeQuery = true)
    int findMaxReqId();

    RequestEntity findByReqIDAndRevID(int reqID , int revID);
}
