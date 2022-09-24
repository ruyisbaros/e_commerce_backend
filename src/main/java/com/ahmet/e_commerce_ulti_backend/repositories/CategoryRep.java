package com.ahmet.e_commerce_ulti_backend.repositories;

import com.ahmet.e_commerce_ulti_backend.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRep extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.name like %?1% or c.alias like %?1%  ")
    Page<Category> findAllByKeyWord(String keyword, Pageable pageable);

    @Query("select c from Category c where c.name = ?1")
    Category findByName(String parentName);

    @Query("select c from Category c where c.parent.id = null")
    public List<Category> findRootCategories();
}
