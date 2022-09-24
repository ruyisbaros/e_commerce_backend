package com.ahmet.e_commerce_ulti_backend.DTO.CategoryDTO;

import com.ahmet.e_commerce_ulti_backend.entities.Category;
import com.ahmet.e_commerce_ulti_backend.entities.CategoryImage;
import lombok.Data;

import java.util.List;

@Data
public class CreateUpdateCategory {

    private String name;
    private String alias;
    private String parentName;
    private boolean enabled;
    private String imageId;
}
