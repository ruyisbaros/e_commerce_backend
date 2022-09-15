package com.ahmet.e_commerce_ulti_backend.appUser;

import com.ahmet.e_commerce_ulti_backend.repositories.AppUserRep;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private AppUserRep appUserRep;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser loggedUser = appUserRep.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with %s email is not exist", email
                )));
        return loggedUser;
    }
}
