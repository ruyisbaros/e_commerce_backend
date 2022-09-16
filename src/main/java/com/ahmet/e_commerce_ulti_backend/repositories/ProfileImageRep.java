package com.ahmet.e_commerce_ulti_backend.repositories;

import com.ahmet.e_commerce_ulti_backend.entities.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileImageRep extends JpaRepository<ProfileImage,Long > {

    List<ProfileImage> findByOrderById();

    @Query("select p from ProfileImage p where p.imageId = ?1")
    Optional<ProfileImage> findByImageId(String imageId);
}
