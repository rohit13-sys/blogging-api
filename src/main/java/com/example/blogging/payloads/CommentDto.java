package com.example.blogging.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CommentDto {

    private String id;

    private String content;

    private UserDto commentUser;

    private String addedDate;
}
