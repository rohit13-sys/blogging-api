package com.example.blogging.service;

import com.example.blogging.payloads.JwtAuthRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    UserDetails isvalidUser(JwtAuthRequest request) throws Exception;


    String getLoggedInUserName();
}
