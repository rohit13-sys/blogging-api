package com.example.blogging.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.validation.constraints.*;
import java.util.*;


@Getter
@Setter
@Data
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 4,message = "Name should be more than 4 characters")
    private String name;

    @Email
    private String email;

    @Size(min = 4,max = 10,message = "password should be more than 4 characters and less than 20")

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private String password;

    @Size(min = 4,message = "about should be more than 4 characters")
    @NotEmpty
    private String about;

    private List<RoleDto> roles=new ArrayList<>();


}
