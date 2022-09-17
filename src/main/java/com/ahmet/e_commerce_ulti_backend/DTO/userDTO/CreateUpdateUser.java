package com.ahmet.e_commerce_ulti_backend.DTO.userDTO;

import com.ahmet.e_commerce_ulti_backend.entities.Role;
import lombok.Data;

import java.util.List;

@Data
public class CreateUpdateUser {

    private String email;
    private String firstName;
    private String lastName;
    private String roleName;
    private String password;
    private String imageId;
    private boolean isEnabled;
    private List<Role> roles;


}
