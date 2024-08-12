package com.requestapproval.requestapproval.Model.Role;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(UserRoleId.class)
@Table(name = "user_role")
public class UserRoleEntity {

    @Id
    @Column(name = "id")
    Integer  id;

    @Id
    @Column(name = "usr_id")
    String usrId;

    @Id
    @Column(name = "role_id")
    String roleId;

    @Column(name = "Status")
    String status;
}
