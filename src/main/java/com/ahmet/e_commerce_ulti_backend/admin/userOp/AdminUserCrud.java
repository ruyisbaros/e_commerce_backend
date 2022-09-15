package com.ahmet.e_commerce_ulti_backend.admin.userOp;

import com.ahmet.e_commerce_ulti_backend.DTO.userDTO.CreateUpdateUser;
import com.ahmet.e_commerce_ulti_backend.appUser.AppUser;
import com.ahmet.e_commerce_ulti_backend.entities.Role;
import com.ahmet.e_commerce_ulti_backend.repositories.AppUserRep;
import com.ahmet.e_commerce_ulti_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@AllArgsConstructor
public class AdminUserCrud {

    private UserService userService;
    private AppUserRep appUserRep;

    @GetMapping("/all")
    public List<AppUser> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/is_email_unique/{email}")
    public boolean check_email(@PathVariable String email) {
        return appUserRep.findByEmail(email).isPresent();
    }

    @PostMapping("/create_user")
    public AppUser createNewUser(@RequestBody CreateUpdateUser request) {
        return userService.createNewUser(request);
    }


    @GetMapping("/get_user/{userId}")
    public AppUser findUser(@PathVariable Long userId) {
        return userService.findUser(userId);
    }

    @DeleteMapping("/delete_user/{userId}")
    public void deleteUser(@PathVariable long userId){
        userService.deleteUser(userId);
    }
    //update general
    @PutMapping("/update_user/{userId}")
    public AppUser updateUser(@RequestBody CreateUpdateUser request, @PathVariable Long userId) {
        return userService.updateUser(userId, request);
    }

    //Update Enabled-disabled
    @PutMapping("/user_enabled_disabled/{userId}")
    public void updateEnableSt(@PathVariable long userId){
        userService.updateEnableStatus(userId);
    }

    @GetMapping("/get_roles")
    public List<Role> getRoles() {
        return userService.findAllRoles();
    }
}
