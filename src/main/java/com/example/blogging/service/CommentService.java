package com.example.blogging.service;

import com.example.blogging.entity.Comment;
import com.example.blogging.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer userId,int postId);

    void deleteComment(int id);

    List<CommentDto> getComments(Integer userId,Integer postId);
}
