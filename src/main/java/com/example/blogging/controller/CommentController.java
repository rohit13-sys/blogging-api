package com.example.blogging.controller;

import com.example.blogging.payloads.CommentDto;
import com.example.blogging.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create-comment/user/{user-id}/post/{post-id}")
    public ResponseEntity<Object> createComment(@RequestBody CommentDto commentDto,@PathVariable("user-id") Integer userId,
                                                @PathVariable("post-id") int postId){

        commentDto=commentService.createComment(commentDto,userId,postId);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-comment/{comment-id}")
    public ResponseEntity<Object> deleteComments(@PathVariable("comment-id") Integer id){
       commentService.deleteComment(id);
       return new ResponseEntity<>("Comments with id : "+id+" Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/get-comments/user/{user-id}/post/{post-id}")
    public ResponseEntity<Object> getComments(@PathVariable("user-id") Integer userId,@PathVariable("post-id") Integer postId){
        List<CommentDto> comments=commentService.getComments(userId,postId);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }
}

