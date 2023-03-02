package com.example.blogging.repository;

import com.example.blogging.entity.Category;
import com.example.blogging.entity.Post;
import com.example.blogging.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByCategory(Category category);

    List<Post> findByUser(Users user);

    @Query("select p from Post p where title like %?1% OR content like %?1%")
    List<Post> findByTitleOrContentContains(String keyword);
}
