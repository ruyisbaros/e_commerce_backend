package com.ahmet.e_commerce_ulti_backend.admin.categoryOp;

import com.ahmet.e_commerce_ulti_backend.DTO.CategoryDTO.CreateUpdateCategory;
import com.ahmet.e_commerce_ulti_backend.entities.Category;
//import com.ahmet.e_commerce_ulti_backend.exception.ApiRequestException;
import com.ahmet.e_commerce_ulti_backend.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryCrud {

    private CategoryService categoryService;

    //for categories display UI
    @GetMapping("/all")
    public Page<Category> getCategories(
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "sortDir", defaultValue = "id", required = false) String sortDir,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword
    ) {
        return categoryService.getAllCategories(pageSize, pageNo, sortDir, sortField, keyword);
    }

    @PostMapping("/create")
    public void createCategory(@RequestBody CreateUpdateCategory request){
        categoryService.createNew(request);
    }

    //For create form select options
    @GetMapping("/all_categories")
    public List<Category> categoriesInForm(){
        return categoryService.findFormCategories();
    }
}
