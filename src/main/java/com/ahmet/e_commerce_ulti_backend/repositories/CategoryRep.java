package com.ahmet.e_commerce_ulti_backend.repositories;

import com.ahmet.e_commerce_ulti_backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRep extends JpaRepository<Category, Long> {


}
