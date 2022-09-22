package com.ahmet.e_commerce_ulti_backend.DTO.userDTO;

import com.ahmet.e_commerce_ulti_backend.entities.ProfileImage;
import com.ahmet.e_commerce_ulti_backend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String  email;
    private String firstName;
    private String  jwtToken;
    private ProfileImage profileImage;
    private List<Role> roles;

}
