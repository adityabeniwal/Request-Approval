package com.requestapproval.requestapproval.Model.Request;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "requests")
public class RequestEntity
{
    @Id
    @Column(name = "req_rev_id")
    private int reqRevID;


    @Column(name = "req_id")
    private int reqID;


    @Column(name = "rev_id")
    private int revID;


    @Column(name = "description")
    private String description;


    @Column(name = "amount")
    private String amount;


    @Column(name = "status")
    private String status;


    @Column(name = "creator")
    private String creator;

}
