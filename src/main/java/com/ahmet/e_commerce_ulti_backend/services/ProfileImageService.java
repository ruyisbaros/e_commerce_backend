package com.ahmet.e_commerce_ulti_backend.services;

import com.ahmet.e_commerce_ulti_backend.entities.ProfileImage;
import com.ahmet.e_commerce_ulti_backend.repositories.ProfileImageRep;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProfileImageService {

    private ProfileImageRep profileImageRep;

    public List<ProfileImage> listImages(){
        return profileImageRep.findByOrderById();
    }

    public Optional<ProfileImage> getOne(Long id){
        return profileImageRep.findById(id);
    }

    public void saveImage(ProfileImage profileImage){
        profileImageRep.save(profileImage);
    }

    public void deleteImage(Long id){
        profileImageRep.deleteById(id);
    }

    public boolean isImageExist(Long id){
        return profileImageRep.existsById(id);
    }
}
