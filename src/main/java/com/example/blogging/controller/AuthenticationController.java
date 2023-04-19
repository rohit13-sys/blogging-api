package com.example.blogging.controller;


import com.example.blogging.config.JWTUtil;
import com.example.blogging.entity.Users;
import com.example.blogging.payloads.JwtAuthRequest;
import com.example.blogging.payloads.JwtAuthResponse;
import com.example.blogging.payloads.UserDto;
import com.example.blogging.payloads.UserResponse;
import com.example.blogging.service.AuthService;
import com.example.blogging.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.catalina.session.StandardSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {


    @Value("${my.greeting}")
    private String greeting;
    @Autowired
    private Environment environment;
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ModelMapper mapper;

//    @Value("${my.greeting}")


    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthResponse> generateToken( @Valid @RequestBody JwtAuthRequest request,HttpSession session) {


        UserDetails userDetails= authService.isvalidUser(request);
        String userName= userDetails.getUsername();
        session.setAttribute("userName",userName);
        final String token = jwtUtil.generateToken(userDetails);
        UserDto userDto=userService.getUserByUserName(userDetails.getUsername());
//        UserResponse userResponse=mapper.map(userDto,UserResponse.class);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setToken(token);
        response.setUserDto(userDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto){

        userDto=service.createUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);


    }


    @GetMapping("/hello")
    public String hello(){

        return greeting;
    }

    @PostMapping("/validtoken")
    public ResponseEntity<Object> isTokenValid(@RequestBody String token){
        boolean isTokenExpired=jwtUtil.isTokenExpired(token);
        if(isTokenExpired){
            throw new ExpiredJwtException(null,null,null);
        }
        return ResponseEntity.ok("Token is valid");
    }


}