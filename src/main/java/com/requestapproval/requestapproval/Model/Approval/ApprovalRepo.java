package com.requestapproval.requestapproval.Model.Approval;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRepo extends JpaRepository<ApprovalEntity,Integer> {
}
