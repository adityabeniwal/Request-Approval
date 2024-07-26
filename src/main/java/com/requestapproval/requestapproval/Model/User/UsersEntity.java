package com.requestapproval.requestapproval.Model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class UsersEntity
{
    @Id
    @Column(name="usr_id")
    private String usrId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "DOB")
    private Date DOB;

    @Column(name = "status")
    private String status;
}
