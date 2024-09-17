package com.requestapproval.requestapproval.Model.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<RequestEntity,Integer>
{
    @Query(value = "SELECT max(req_id) FROM requestapproval.requests;", nativeQuery = true)
    int findMaxReqId();

    @Query(value = "select max(rev_id) FROM requestapproval.requests where req_id = :reqID;",nativeQuery = true)
    int findMaxRevIdByReqId(@Param("reqID") int reqID);

    RequestEntity findByReqIDAndRevID(int reqID , int revID);
}
