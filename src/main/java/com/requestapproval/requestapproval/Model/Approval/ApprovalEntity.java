package com.requestapproval.requestapproval.Model.Approval;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "approval")
public class ApprovalEntity
{
    @Id
    @Column(name = "req_rev_id")
    private int reqRevID;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "comment")
    private String comment;

}
