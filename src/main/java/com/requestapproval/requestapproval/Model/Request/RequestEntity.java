package com.requestapproval.requestapproval.Model.Request;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "requests")
public class RequestEntity
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "req_rev_id")
    private int reqRevID;


    @Column(name = "req_id")
    private int reqID;


    @Column(name = "rev_id")
    private int revID;


    @Column(name = "description")
    private String description;


    @Column(name = "amount")
    private int amount;


    @Column(name = "status")
    private String status;


    @Column(name = "creator")
    private String creator;

}
