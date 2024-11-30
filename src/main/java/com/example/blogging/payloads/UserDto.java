package com.example.blogging.payloads;

import com.example.blogging.entity.Post;
import com.example.blogging.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.*;


@Getter
@Setter
@Data
public class UserDto {

    private String id;
    @NotEmpty
    @Size(min = 4,message = "Name should be more than 4 characters")
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 8,max = 50,message = "Password should be more than 8 characters")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,50}$",message ="password should have atleast One uppercase letter and 1 special character and 1 lowercase letter and 1 digit")
    private String password;

    @Size(min = 4,message = "about should be more than 4 characters")
    @NotEmpty
    private String about;

    private List<Role> roles=new ArrayList<>();

    private List<Post> posts = new ArrayList<>();


    @JsonIgnore()
    public String getPassword(){
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password){
        this.password=password;
    }


}
