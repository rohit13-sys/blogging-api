package com.example.blogging.service;


import com.example.blogging.entity.Post;
import com.example.blogging.payloads.PostDto;
import com.example.blogging.payloads.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PostService {


    PostDto createPost(PostDto postDto, int userId, int categoryId);

    PostDto updatePost(PostDto postDto,int id);

    void deletePost(PostDto postDto);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    PostDto getPostById(int postId);

    List<PostDto> getPostByCategory(int categoryId);


    List<PostDto> getPostByUser(int userId);

    List<PostDto> searchPosts(String keyword);


    PostDto uploadImage(String fileName, MultipartFile file, int id) throws IOException;

    InputStream getImage(Integer id) throws FileNotFoundException;
}
