package com.requestapproval.requestapproval.Model.Dual;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name ="DUAL")
public class DualEntity {
    @Id
    @Column(name= "DUMMY")
    String dumy;
}
