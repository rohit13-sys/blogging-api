package com.example.blogging.controller;

import com.example.blogging.payloads.RoleDto;
import com.example.blogging.payloads.UserDto;
import com.example.blogging.service.AuthService;
import com.example.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthService authService;


    @PostMapping("/create-user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto) {

        userDto = service.createUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);


    }

    @GetMapping("/{user-name}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable("user-name") String email) {
        UserDto userDto = new UserDto();

        userDto = service.getUserByUserName(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @PutMapping("/update-user")
    public ResponseEntity<Object> updateUserByEmail(@Valid @RequestBody UserDto userDto) {

        int id = service.getUserIdByUserName(userDto.getEmail());
        userDto = service.updateUser(id, userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Object> deleteUserByEmail(@PathVariable("id") Integer id) {

        service.deleteUserByUserName(id);
        return new ResponseEntity<>("User with id : " + id + " is deleted successfully", HttpStatus.OK);

    }

    @GetMapping("/all-users")
    public ResponseEntity<Object> getAllUsers() {
        List<UserDto> userDtoList = service.getAllUsers();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

}
