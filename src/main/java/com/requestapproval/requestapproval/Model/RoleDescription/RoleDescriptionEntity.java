package com.requestapproval.requestapproval.Model.RoleDescription;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="role_description")
public class RoleDescriptionEntity {
    @Id
    @Column(name = "role_id")
    String roleId;
    @Column(name = "role_description")
    String roleDescription;
    @Column(name = "name")
    String name;
}
