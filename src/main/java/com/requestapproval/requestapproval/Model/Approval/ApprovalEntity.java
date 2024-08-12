package com.requestapproval.requestapproval.Model.Approval;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "approval")
public class ApprovalEntity
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private int approvalId;

    @Column(name = "req_rev_id")
    private int reqRevID;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "comment")
    private String comment;

}
