package com.ahmet.e_commerce_ulti_backend.uploads;

import com.ahmet.e_commerce_ulti_backend.entities.CategoryImage;
import com.ahmet.e_commerce_ulti_backend.messages.Message;
import com.ahmet.e_commerce_ulti_backend.repositories.CategoryImageRep;
import com.ahmet.e_commerce_ulti_backend.services.CategoryImageService;
import com.ahmet.e_commerce_ulti_backend.services.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/company/categories/images")
@AllArgsConstructor
@CrossOrigin
public class CategoryImageController {

    private CloudinaryService cloudinaryService;

    private CategoryImageService imageService;

    private CategoryImageRep categoryImageRep;

    @GetMapping("/all")
    public List<CategoryImage> getAll() {
       return categoryImageRep.findAll();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        Map result = cloudinaryService.uploadImage(multipartFile);
        CategoryImage categoryImage = new CategoryImage((String) result.get("public_id"), (String) result.get("url"));
        imageService.saveImage(categoryImage);
        return new ResponseEntity(categoryImage, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id) throws IOException {
        if (imageService.isImageExist(id)) {
            CategoryImage categoryImage = categoryImageRep.findByImageId(id).get();
            //System.out.println(categoryImage);
            Map result = cloudinaryService.deleteImage(categoryImage.getImageId());
            imageService.deleteImage(id);
            return new ResponseEntity(new Message("Image has been deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("Image is not exist"), HttpStatus.BAD_REQUEST);
        }
    }
}
