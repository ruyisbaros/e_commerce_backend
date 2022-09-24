package com.ahmet.e_commerce_ulti_backend.repositories;

import com.ahmet.e_commerce_ulti_backend.entities.CategoryImage;
import com.ahmet.e_commerce_ulti_backend.entities.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryImageRep extends JpaRepository<CategoryImage,Long> {

    List<ProfileImage> findByOrderById();

    @Query("select c from CategoryImage c where c.imageId = ?1")
    Optional<CategoryImage> findByImageId(String imageId);

    void deleteByImageId(String imageId);
}
