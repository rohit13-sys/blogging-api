package com.example.blogging.service;

import com.example.blogging.payloads.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto getRole(String roleName);

    RoleDto addRole(RoleDto roleDto);

    List<RoleDto> getAllRoles();
}
