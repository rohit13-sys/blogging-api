package com.example.blogging.service.impl;

import com.example.blogging.entity.Role;
import com.example.blogging.exceptions.RoleAlreadyExistsException;
import com.example.blogging.exceptions.RoleNotFoundException;
import com.example.blogging.payloads.RoleDto;
import com.example.blogging.repository.RoleRepository;
import com.example.blogging.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public RoleDto getRole(String roleName) {
      Role role=roleRepository.findByName(roleName).orElseThrow(()->new RoleNotFoundException());
      RoleDto roleDto=modelMapper.map(role,RoleDto.class);
      return roleDto;
    }

    @Override
    public RoleDto addRole(RoleDto roleDto) {
        try{
            RoleDto savedRole=getRole(roleDto.getName());
            if(savedRole!=null){
                throw new RoleAlreadyExistsException();

            }else {
                throw new RoleNotFoundException();
            }
        }catch (RoleNotFoundException e){
            Role role=dtoToRole(roleDto);
            role=roleRepository.save(role);
            roleDto=roleToDto(role);
            return roleDto;
        }
    }

    @Override
    public List<RoleDto> getAllRoles() {
    List<Role> roles=roleRepository.findAll();
    List<RoleDto> roleDtos=roles.stream().map(role->roleToDto(role)).collect(Collectors.toList());
    return roleDtos;
    }


    RoleDto roleToDto(Role role){
        RoleDto roleDto=modelMapper.map(role,RoleDto.class);
        return roleDto;
    }

    Role dtoToRole(RoleDto roleDto){
        Role role=modelMapper.map(roleDto, Role.class);
        return role;
    }


}
