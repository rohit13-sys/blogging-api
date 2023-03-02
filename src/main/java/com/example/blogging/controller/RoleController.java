package com.example.blogging.controller;

import com.example.blogging.payloads.RoleDto;
import com.example.blogging.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create-role")
    public ResponseEntity<Object> createRole(@RequestBody RoleDto roleDto){
        roleDto=roleService.addRole(roleDto);
        return new ResponseEntity<>(roleDto, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-roles")
    public ResponseEntity<Object> getAllRoles(){
        List<RoleDto> roleDtos=roleService.getAllRoles();
        return new ResponseEntity<>(roleDtos,HttpStatus.OK);
    }

}
