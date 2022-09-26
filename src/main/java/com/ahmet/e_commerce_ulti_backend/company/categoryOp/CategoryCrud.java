package com.ahmet.e_commerce_ulti_backend.company.categoryOp;

import com.ahmet.e_commerce_ulti_backend.DTO.CategoryDTO.CreateUpdateCategory;
import com.ahmet.e_commerce_ulti_backend.appUser.AppUser;
import com.ahmet.e_commerce_ulti_backend.appUser.UserCsvExporter;
import com.ahmet.e_commerce_ulti_backend.entities.Category;
//import com.ahmet.e_commerce_ulti_backend.exception.ApiRequestException;
import com.ahmet.e_commerce_ulti_backend.repositories.CategoryRep;
import com.ahmet.e_commerce_ulti_backend.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/company/categories")
@AllArgsConstructor
public class CategoryCrud {

    private CategoryService categoryService;

    private CategoryRep categoryRep;

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
    public void createCategory(@RequestBody CreateUpdateCategory request) {
        categoryService.createNew(request);
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    //update General
    @PutMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @RequestBody CreateUpdateCategory request) {
        return categoryService.updateCatg(id, request);
    }

    //Update Enabled-disabled
    @PutMapping("/category_enabled_disabled/{id}")
    public void updateEnableSt(@PathVariable long id) {
        categoryService.updateEnableStatus(id);
    }

    //For create form select options
    @GetMapping("/all_categories")
    public List<Category> categoriesInForm() {
        return categoryService.findFormCategories();
    }

    @GetMapping("/name_unique/{name}")
    public boolean isNameUnique(@PathVariable String name) {
        Category byName = categoryRep.findByName(name);
        return byName != null;
    }

    @GetMapping("/alias_unique/{alias}")
    public boolean isAliasUnique(@PathVariable String alias) {
        Category byAlias = categoryRep.findByAlias(alias);
        return byAlias != null;
    }

    @DeleteMapping("delete/{id}")
    public void deleteCategory(@PathVariable Long id) throws IOException {
        categoryService.deleteCategory(id);
    }

    //Export Users info as CSV Format
    @GetMapping("/export_csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        List<Category> categories = categoryService.findFormCategories();
        CategoryCsvExporter csvExporter = new CategoryCsvExporter();
        csvExporter.export(categories, response);
    }
}
