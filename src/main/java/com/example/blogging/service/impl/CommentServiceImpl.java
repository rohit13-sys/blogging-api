package com.example.blogging.service.impl;

import com.example.blogging.entity.Comment;
import com.example.blogging.entity.Post;
import com.example.blogging.entity.Users;
import com.example.blogging.exceptions.CommentNotFoundException;
import com.example.blogging.exceptions.PostNotFoundException;
import com.example.blogging.payloads.CommentDto;
import com.example.blogging.payloads.UserDto;
import com.example.blogging.repository.CommentRepository;
import com.example.blogging.repository.PostRepository;
import com.example.blogging.service.CommentService;
import com.example.blogging.service.PostService;
import com.example.blogging.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, String userId, String postId) {
        UserDto userDto=userService.getUserById(userId);
//        PostDto postdto = postService.getPostById(postId);
        Post post=postRepository.findById(postId).orElseThrow(()->new PostNotFoundException("Post not found"));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setCommentedUser(modelMapper.map(userDto, Users.class));
        comment.setPost(post);
        comment.setAddedDate(new Date().toString());
        comment = commentRepository.save(comment);

        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public void deleteComment(String commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment Not Found"));
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getComments(Integer userId,Integer postId) {
        List<Comment> comments=commentRepository.findAllByCommentedUserIdAndPostId(userId,postId);
        List<CommentDto> commentDtos=comments.stream().map((comment -> modelMapper.map(comment,CommentDto.class))).collect(Collectors.toList());
        System.out.println(commentDtos);
        return commentDtos;
    }
}
