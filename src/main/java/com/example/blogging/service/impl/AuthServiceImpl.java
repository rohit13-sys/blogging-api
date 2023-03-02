package com.example.blogging.service.impl;

import com.example.blogging.exceptions.InvalidCredentialsException;
import com.example.blogging.payloads.JwtAuthRequest;
import com.example.blogging.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails isvalidUser(JwtAuthRequest request) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        if (passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            return userDetails;
        } else {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public String getLoggedInUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
