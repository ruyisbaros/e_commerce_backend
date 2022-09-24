package com.ahmet.e_commerce_ulti_backend.services;

import com.ahmet.e_commerce_ulti_backend.entities.CategoryImage;
import com.ahmet.e_commerce_ulti_backend.repositories.CategoryImageRep;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class CategoryImageService {

    private CategoryImageRep categoryImageRep;

    public void saveImage(CategoryImage categoryImage) {
        categoryImageRep.save(categoryImage);
    }

    public boolean isImageExist(String id) {
        return categoryImageRep.findByImageId(id).isPresent();
    }

    public void deleteImage(String id) {
        categoryImageRep.deleteByImageId(id);
    }
}
