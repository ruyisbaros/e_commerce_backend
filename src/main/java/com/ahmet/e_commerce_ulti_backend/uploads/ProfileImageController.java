package com.ahmet.e_commerce_ulti_backend.uploads;

import com.ahmet.e_commerce_ulti_backend.entities.ProfileImage;
import com.ahmet.e_commerce_ulti_backend.messages.Message;
import com.ahmet.e_commerce_ulti_backend.repositories.ProfileImageRep;
import com.ahmet.e_commerce_ulti_backend.services.CloudinaryService;
import com.ahmet.e_commerce_ulti_backend.services.ProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/users/images/")
@CrossOrigin
public class ProfileImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ProfileImageService profileImageService;

    @Autowired
    private ProfileImageRep profileImageRep;

    @GetMapping("/list_all")
    public ResponseEntity<List<ProfileImage>> listImages() {
        List<ProfileImage> list = profileImageService.listImages();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile multipartFile) throws IOException {
        Map result = cloudinaryService.uploadImage(multipartFile);
        ProfileImage profileImage =
                new ProfileImage((String) result.get("public_id"), (String) result.get("url"));

        profileImageService.saveImage(profileImage);

        return new ResponseEntity(profileImage, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id) throws IOException {
        if (profileImageService.isImageExist(id)) {
            ProfileImage image = profileImageRep.findByImageId(id).get();
            Map result = cloudinaryService.deleteImage(image.getImageId());
            profileImageService.deleteImage(id);
            return new ResponseEntity(new Message("Image has been deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("Image is not exist"), HttpStatus.BAD_REQUEST);
        }

    }

}
