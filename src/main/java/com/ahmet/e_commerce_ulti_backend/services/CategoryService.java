package com.ahmet.e_commerce_ulti_backend.services;

import com.ahmet.e_commerce_ulti_backend.DTO.CategoryDTO.CreateUpdateCategory;
import com.ahmet.e_commerce_ulti_backend.entities.Category;
import com.ahmet.e_commerce_ulti_backend.entities.CategoryImage;
import com.ahmet.e_commerce_ulti_backend.messages.Message;
import com.ahmet.e_commerce_ulti_backend.repositories.CategoryImageRep;
import com.ahmet.e_commerce_ulti_backend.repositories.CategoryRep;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRep categoryRep;
    private CategoryImageRep categoryImageRep;

    private CloudinaryService cloudinaryService;

    public Page<Category> getAllCategories(int pageSize, int pageNo, String sortDir,
                                           String sortField, String keyword) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

//        List<Category> rootCategories = categoryRep.findRootCategories();
//        List<Category> categories = listHierarchicalCategories(rootCategories);
//        for (Category c : categories) {
//            System.out.println(c.getName());
//        }

        return categoryRep.findAllByKeyWord(keyword, pageable);

    }

//    private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
//        List<Category> hierarchicalCategories = new ArrayList<>();
//        for (Category rootCategory : rootCategories) {
//            hierarchicalCategories.add(Category.copyFull(rootCategory));
//
//            List<Category> rootCategoryChildren = rootCategory.getChildren();
//
//            for (Category children : rootCategoryChildren) {
//                //System.out.println(children);
//                hierarchicalCategories.add(Category.copyFull(children));
//                if (children.getChildren() != null) {
//                    List<Category> grandSons = children.getChildren();
//                    for (Category grandsn : grandSons) {
//                        hierarchicalCategories.add(Category.copyFull(grandsn));
//                    }
//                }
//            }
//        }
//        return hierarchicalCategories;
//    }

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

    public String updateCatg(Long id, CreateUpdateCategory request) {
        Category toUpdate = categoryRep.findById(id).get();
        if (toUpdate != null) {
            if (!request.getImageId().isEmpty()) {
                List<CategoryImage> images = new ArrayList<>();
                CategoryImage image = categoryImageRep.findByImageId(request.getImageId()).get();
                images.add(image);
                toUpdate.setImages(images);
            }
            if (request.getParentName()!=null) {
                Category parentCategory = categoryRep.findByName(request.getParentName());
                toUpdate.setParent(parentCategory);
            } else {
                toUpdate.setParent(null);
            }
            toUpdate.setName(request.getName());
            toUpdate.setAlias(request.getAlias());
            categoryRep.save(toUpdate);
            return (String.format("Category with %s Id has been updated", id));
        } else {
            throw new UsernameNotFoundException(String.format("Category with %s Id is not exist", id));
        }
    }

    public void updateEnableStatus(long id) {

        Category toUpdate = categoryRep.findById(id).get();

        if (toUpdate != null) {
            if(toUpdate.isEnabled()){
                toUpdate.setEnabled(false);
                categoryRep.save(toUpdate);
            }else{
                toUpdate.setEnabled(true);
                categoryRep.save(toUpdate);
            }

        } else {
            throw new UsernameNotFoundException(String.format("Category with %s Id is not exist", id));
        }
    }

    public Category getCategory(Long id) {
        return categoryRep.findById(id).get();
    }

    public void deleteCategory(Long id) throws IOException {

        Optional<Category> category=categoryRep.findById(id);
        if(category.isPresent()){
            //First let's delete images from cloud
            List<CategoryImage> categoryImages=category.get().getImages();
            for(CategoryImage image:categoryImages){
                cloudinaryService.deleteImage(image.getImageId());
            }
            categoryRep.deleteById(category.get().getId());
        }else{
            throw new UsernameNotFoundException(String.format("Category with %s ID is not exist",id));
        }
    }
}

