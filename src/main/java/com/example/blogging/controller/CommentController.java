package com.example.blogging.controller;

import com.example.blogging.payloads.CommentDto;
import com.example.blogging.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create-comment/{post-id}")
    public ResponseEntity<Object> createComment(@RequestBody CommentDto commentDto,@PathVariable("post-id") int postId){

        commentDto=commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-comment/{comment-id}")
    public ResponseEntity<Object> deleteComments(@PathVariable("comment-id") Integer id){
       commentService.deleteComment(id);
       return new ResponseEntity<>("Comments with id : "+id+" Deleted Successfully",HttpStatus.OK);
    }
}

