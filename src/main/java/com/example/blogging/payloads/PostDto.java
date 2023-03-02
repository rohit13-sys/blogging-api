package com.example.blogging.payloads;

import com.example.blogging.entity.Category;
import com.example.blogging.entity.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PostDto {

    private int id;
    private String title;

    private String imageName;

    private String content;

    private Date addedDate;


    private CategoryDto category;


    private UserDto user;

    private Set<CommentDto> comments=new HashSet<>();
}
