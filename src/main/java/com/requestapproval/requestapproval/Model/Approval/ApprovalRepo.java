package com.requestapproval.requestapproval.Model.Approval;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepo extends JpaRepository<ApprovalEntity,Integer> {
    public List<ApprovalEntity> findAllByReqRevID(int reqRevID);

    public ApprovalEntity findByReqRevIDAndRoleId(int reqRevID, String roleId);
}
