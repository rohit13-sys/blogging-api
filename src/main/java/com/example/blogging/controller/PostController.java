package com.example.blogging.controller;

import com.example.blogging.entity.Post;
import com.example.blogging.exceptions.PostNotFoundException;
import com.example.blogging.payloads.PostDto;
import com.example.blogging.payloads.PostResponse;
import com.example.blogging.service.AuthService;
import com.example.blogging.service.FileService;
import com.example.blogging.service.PostService;
import com.example.blogging.utils.Constants;
import com.mysql.fabric.Response;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @Value("${project-image}")
    String uploadDir;


    @PostMapping("/user/{user-id}/category/{category-id}/posts")
    public ResponseEntity<Object> createPost(@RequestBody PostDto postDto,
                                             @PathVariable("user-id") Integer userId, @PathVariable("category-id") int categoryId) {
        PostDto post = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/user/{user-id}/posts")
    public ResponseEntity<Object> getPostsByUser(@PathVariable("user-id") Integer userId,
                                                 @RequestParam(value = "pageNo", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize,
                                                 @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
                                                 @RequestParam(value = "sortDir", defaultValue = Constants.SORT_ORDER, required = false) String sortDir) {
        PostResponse postResponse = postService.getPostByUser(userId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/category/{category-id}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("category-id") Integer categoryId

    ) {
        List<PostDto> postResponse = postService.getPostByCategory(categoryId);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("all-posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNo", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize,
                                              @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
                                              @RequestParam(value = "sortDir", defaultValue = Constants.SORT_ORDER, required = false) String sortDir) {
        PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }


    @DeleteMapping("/delete-posts/{post-id}")
    public ResponseEntity<Object> deletePostById(@PathVariable("post-id") Integer id) throws IOException {

        PostDto postDto = postService.getPostById(id);


        postService.deletePost(postDto);

        return new ResponseEntity<>("Post : " + postDto.getTitle() + " is deleted successfully", HttpStatus.OK);


    }

    @PutMapping("/update-posts/{post-id}")
    public ResponseEntity<Object> updatePostById(@RequestBody PostDto postDto, @PathVariable("post-id") int id) {
        postDto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("search-posts/{keyword}")
    public ResponseEntity<Object> searchPosts(@PathVariable("keyword") String keyword) {
        List<PostDto> postDtos = postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/image-upload/{post-id}")
    public ResponseEntity<Object> uploadImage(@PathVariable("post-id") Integer postId, @RequestParam(value = "image") MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        PostDto postDto = postService.uploadImage(fileName, file, postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }


    @GetMapping(value = "/image/{postId}")
    public @ResponseBody byte[] serveImage(@PathVariable("postId") Integer postId, HttpServletResponse response) throws IOException {
        InputStream is = postService.getImage(postId);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        return IOUtils.toByteArray(is);
    }

    @GetMapping("/posts/{post-id}")
    public ResponseEntity<Object> getPostById(@PathVariable("post-id") Integer postId) {

        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

}
