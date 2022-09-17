package com.ahmet.e_commerce_ulti_backend.services;

import com.ahmet.e_commerce_ulti_backend.DTO.userDTO.CreateUpdateUser;
import com.ahmet.e_commerce_ulti_backend.appUser.AppUser;
import com.ahmet.e_commerce_ulti_backend.entities.ProfileImage;
import com.ahmet.e_commerce_ulti_backend.entities.Role;
import com.ahmet.e_commerce_ulti_backend.repositories.AppUserRep;
import com.ahmet.e_commerce_ulti_backend.repositories.ProfileImageRep;
import com.ahmet.e_commerce_ulti_backend.repositories.RoleRep;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private AppUserRep appUserRep;
    private RoleRep roleRep;

    private PasswordEncoder passwordEncoder;

    private ProfileImageService profileImageService;

    private ProfileImageRep profileImageRep;

    public List<AppUser> getAllUsers() {
        return appUserRep.findAll();
    }

    public AppUser createNewUser(CreateUpdateUser request) {

        boolean isExist = appUserRep.findByEmail(request.getEmail()).isPresent();

        Optional<ProfileImage> profileImage = profileImageRep.findByImageId(request.getImageId());

        if (!isExist) {
            AppUser appUser = new AppUser();
            List<Role> roles = request.getRoles();
            for (Role r : roles) {
                appUser.addRole(r);
            }
            appUser.setFirstName(request.getFirstName());
            appUser.setLastName(request.getLastName());
            appUser.setEmail(request.getEmail());
            appUser.setPassword(passwordEncoder.encode(request.getPassword()));
            appUser.setProfileImage(profileImage.get());
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
        Optional<ProfileImage> profileImage = profileImageRep.findByImageId(request.getImageId());
        AppUser appUser = appUserRep.findById(userId).get();
        List<Role> roles = new ArrayList<>();
        for (Role r : request.getRoles()) {
            roles.add(new Role(r.getRoleName()));
        }
        appUser.setFirstName(request.getFirstName());
        appUser.setLastName(request.getLastName());
        appUser.setEmail(request.getEmail());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setProfileImage(profileImage.get());
        appUser.setEnabled(request.isEnabled());
        appUser.setRoles((roles));
        return appUserRep.save(appUser);


    }

    public AppUser findUser(Long userId) {
        return appUserRep.findById(userId).get();

    }

    public void deleteUser(long userId) {
        boolean isExist = appUserRep.findById(userId).isPresent();
        if (isExist) {
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
}
