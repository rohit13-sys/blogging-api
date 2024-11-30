package com.example.blogging.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
public class UserResponse {

    private String id;

    private String name;

    private String email;

    private String about;

    private List<RoleDto> roles=new ArrayList<>();
}
