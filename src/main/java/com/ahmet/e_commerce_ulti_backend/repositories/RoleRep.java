package com.ahmet.e_commerce_ulti_backend.repositories;

import com.ahmet.e_commerce_ulti_backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRep extends JpaRepository<Role, String> {

    @Query("select r from Role r where r.roleName = ?1")
    Optional<Role> findByRoleName(String roleName);
}
