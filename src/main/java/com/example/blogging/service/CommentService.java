package com.example.blogging.service;

import com.example.blogging.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, String userId, String postId);

    void deleteComment(String id);

    List<CommentDto> getComments(Integer userId,Integer postId);
}
