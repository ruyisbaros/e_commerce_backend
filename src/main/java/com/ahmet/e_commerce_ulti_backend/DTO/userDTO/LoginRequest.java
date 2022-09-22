package com.ahmet.e_commerce_ulti_backend.DTO.userDTO;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
