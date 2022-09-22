package com.ahmet.e_commerce_ulti_backend.jwt;

import com.ahmet.e_commerce_ulti_backend.appUser.AppUser;
import com.ahmet.e_commerce_ulti_backend.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class FilterJwt extends OncePerRequestFilter {

    private AppUserService appUserService;
    private CreateJwt createJwt;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        //System.out.println(header);

        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.split(" ")[1].trim();

        if (!createJwt.isValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        String email = createJwt.getSubject(token);

        AppUser userDetails = (AppUser) appUserService.loadUserByUsername(email);
        //System.out.println(userDetails);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
