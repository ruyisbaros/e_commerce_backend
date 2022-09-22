package com.ahmet.e_commerce_ulti_backend.controllers;

import com.ahmet.e_commerce_ulti_backend.DTO.userDTO.LoginRequest;
import com.ahmet.e_commerce_ulti_backend.DTO.userDTO.LoginResponse;
import com.ahmet.e_commerce_ulti_backend.appUser.AppUser;
import com.ahmet.e_commerce_ulti_backend.jwt.CreateJwt;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private CreateJwt createJwt;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            AppUser user = (AppUser) authentication.getPrincipal();
            String jwtToken = createJwt.createToken(user);
            LoginResponse response = new LoginResponse(user.getEmail(), user.getFirstName(), jwtToken, user.getProfileImage(), user.getRoles());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }


}
