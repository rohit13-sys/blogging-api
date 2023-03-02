package com.example.blogging.service;

import com.example.blogging.exceptions.UserAlreadyExists;
import com.example.blogging.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto) throws UserAlreadyExists;

    UserDto updateUser(int id,UserDto userDto);

    UserDto getUserByUserName(String userName);


    List<UserDto> getAllUsers();
    void deleteUserByUserName(int id);
    int getUserIdByUserName(String userName);

    UserDto getUserById(Integer id);
}
