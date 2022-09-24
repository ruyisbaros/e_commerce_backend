package com.ahmet.e_commerce_ulti_backend.services;

import com.ahmet.e_commerce_ulti_backend.DTO.CategoryDTO.CreateUpdateCategory;
import com.ahmet.e_commerce_ulti_backend.entities.Category;
import com.ahmet.e_commerce_ulti_backend.entities.CategoryImage;
import com.ahmet.e_commerce_ulti_backend.repositories.CategoryImageRep;
import com.ahmet.e_commerce_ulti_backend.repositories.CategoryRep;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRep categoryRep;
    private CategoryImageRep categoryImageRep;

    public Page<Category> getAllCategories(int pageSize, int pageNo, String sortDir,
                                           String sortField, String keyword) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        //Sort sort=

        return categoryRep.findAllByKeyWord(keyword, pageable);

    }

    public List<Category> getAll() {
        return categoryRep.findAll();
    }

    public void createNew(CreateUpdateCategory request) {

        Category newCategory = new Category();
        if (request.getImageId() != null) {
            List<CategoryImage> images = new ArrayList<>();
            CategoryImage categoryImage = categoryImageRep.findByImageId(request.getImageId()).get();
            images.add(categoryImage);
            newCategory.setImages(images);
        }

        if (request.getParentName() != null) {
            Category parentCategory = categoryRep.findByName(request.getParentName());
            newCategory.setParent(parentCategory);
        } else {
            newCategory.setParent(null);
        }
        newCategory.setName(request.getName());
        newCategory.setAlias(request.getAlias());
        newCategory.setEnabled(request.isEnabled());

        categoryRep.save(newCategory);
    }

    public List<Category> findFormCategories() {
        return categoryRep.findAll();
    }
}
