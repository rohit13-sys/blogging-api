package com.example.blogging.service;


import com.example.blogging.payloads.PostDto;
import com.example.blogging.payloads.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PostService {


    PostDto createPost(PostDto postDto, String userId, String categoryId);

    PostDto updatePost(PostDto postDto, String id);

    void deletePost(PostDto postDto) throws IOException;

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    PostDto getPostById(String postId);

    List<PostDto> getPostByCategory(String categoryId);


    PostResponse getPostByUser(String userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<PostDto> searchPosts(String keyword);


    PostDto uploadImage(String fileName, MultipartFile file, String id) throws IOException;

    InputStream getImage(String id) throws FileNotFoundException;

    void storeLikeCounts(String postId, Long likeCounts, Long dislikeCounts);
}
