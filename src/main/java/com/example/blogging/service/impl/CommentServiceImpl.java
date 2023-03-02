package com.example.blogging.service.impl;

import com.example.blogging.entity.Comment;
import com.example.blogging.entity.Post;
import com.example.blogging.exceptions.CommentNotFoundException;
import com.example.blogging.exceptions.PostNotFoundException;
import com.example.blogging.payloads.CommentDto;
import com.example.blogging.repository.CommentRepository;
import com.example.blogging.repository.PostRepository;
import com.example.blogging.service.CommentService;
import com.example.blogging.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found"));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment = commentRepository.save(comment);

        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public void deleteComment(int commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment Not Found"));
        commentRepository.delete(comment);
    }
}
