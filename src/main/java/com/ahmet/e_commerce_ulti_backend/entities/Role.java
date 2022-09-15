package com.ahmet.e_commerce_ulti_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
    private String description;



    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }
}