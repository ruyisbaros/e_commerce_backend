package com.ahmet.e_commerce_ulti_backend.repositories;

import com.ahmet.e_commerce_ulti_backend.appUser.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRep extends JpaRepository<AppUser, Long> {

    @Query("select u from AppUser u where u.email = ?1")
    Optional<AppUser> findByEmail(String email);

    @Query("select u from AppUser u where u.firstName like %?1% or u.lastName like %?1% or u.email like %?1% ")
    public Page<AppUser> findAllByKeyWord(String keyword, Pageable pageable);
}
