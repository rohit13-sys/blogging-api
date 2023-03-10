package com.example.blogging.repository;

import com.example.blogging.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {


    List<Comment> findAllByCommentedUserIdAndPostId(Integer userId,Integer postId);
}
