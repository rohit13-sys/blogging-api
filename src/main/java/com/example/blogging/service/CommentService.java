package com.example.blogging.service;

import com.example.blogging.entity.Comment;
import com.example.blogging.payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,int postId);

    void deleteComment(int id);
}
