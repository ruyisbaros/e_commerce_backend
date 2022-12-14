package com.ahmet.e_commerce_ulti_backend.services;

import com.ahmet.e_commerce_ulti_backend.DTO.userDTO.CreateUpdateUser;
import com.ahmet.e_commerce_ulti_backend.appUser.AppUser;
import com.ahmet.e_commerce_ulti_backend.entities.ProfileImage;
import com.ahmet.e_commerce_ulti_backend.entities.Role;
import com.ahmet.e_commerce_ulti_backend.repositories.AppUserRep;
import com.ahmet.e_commerce_ulti_backend.repositories.ProfileImageRep;
import com.ahmet.e_commerce_ulti_backend.repositories.RoleRep;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private AppUserRep appUserRep;
    private RoleRep roleRep;

    private PasswordEncoder passwordEncoder;

    private ProfileImageService profileImageService;

    private ProfileImageRep profileImageRep;

    private CloudinaryService cloudinaryService;

    public Page<AppUser> getAllUsers(int pageSize, int pageNo, String sortDir, String sortField, String keyword) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        //Sort sort=

        return appUserRep.findAllByKeyWord(keyword, pageable);
    }

    public AppUser createNewUser(CreateUpdateUser request) {

        boolean isExist = appUserRep.findByEmail(request.getEmail()).isPresent();

        if (!isExist) {
            AppUser appUser = new AppUser();
            List<Role> roles = request.getRoles();
            for (Role r : roles) {
                appUser.addRole(r);
            }
            if (!request.getImageId().isEmpty()) {
                Optional<ProfileImage> profileImage = profileImageRep.findByImageId(request.getImageId());
                appUser.setProfileImage(profileImage.get());
            }
            appUser.setFirstName(request.getFirstName());
            appUser.setLastName(request.getLastName());
            appUser.setEmail(request.getEmail());
            appUser.setPassword(passwordEncoder.encode(request.getPassword()));

            appUser.setEnabled(true);
            //System.out.println(request);
            return appUserRep.save(appUser);
        } else {
            throw new IllegalStateException(String.format(" %s email is already exist. Please use another email", request.getEmail()));
        }

    }

    public List<Role> findAllRoles() {
        return roleRep.findAll();
    }

    public AppUser updateUser(Long userId, CreateUpdateUser request) {

        AppUser appUser = appUserRep.findById(userId).get();
        List<Role> roles = new ArrayList<>();
        for (Role r : request.getRoles()) {
            roles.add(new Role(r.getRoleName()));
        }
        if (!request.getImageId().isEmpty()) {
            Optional<ProfileImage> profileImage = profileImageRep.findByImageId(request.getImageId());
            appUser.setProfileImage(profileImage.get());
        }
        appUser.setFirstName(request.getFirstName());
        appUser.setLastName(request.getLastName());
        appUser.setEmail(request.getEmail());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setEnabled(request.isEnabled());
        appUser.setRoles((roles));
        return appUserRep.save(appUser);


    }

    public AppUser findUser(Long userId) {
        return appUserRep.findById(userId).get();

    }

    public void deleteUser(long userId) throws IOException {
        Optional<AppUser> appUser = appUserRep.findById(userId);
        if (appUser.isPresent()) {
            ProfileImage userImage=appUser.get().getProfileImage();
            cloudinaryService.deleteImage(userImage.getImageId());
            appUserRep.deleteById(userId);
        } else {
            throw new UsernameNotFoundException(String.format("user with %s ID is not exist", userId));
        }
    }

    public void updateEnableStatus(long userId) {
        AppUser targetUser = appUserRep.findById(userId).get();
        if (targetUser.isEnabled() == false) {
            targetUser.setEnabled(true);
            appUserRep.save(targetUser);
        } else {
            targetUser.setEnabled(false);
            appUserRep.save(targetUser);
        }
    }


    public List<AppUser> listAllUsers() {
        return appUserRep.findAll();
    }
}
