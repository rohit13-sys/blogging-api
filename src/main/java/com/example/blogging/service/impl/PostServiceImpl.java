package com.example.blogging.service.impl;

import com.example.blogging.entity.Category;
import com.example.blogging.entity.Post;
import com.example.blogging.entity.Users;
import com.example.blogging.exceptions.CategoryNotFound;
import com.example.blogging.exceptions.PostNotFoundException;
import com.example.blogging.exceptions.UserNotFound;
import com.example.blogging.payloads.PostDto;
import com.example.blogging.payloads.PostResponse;
import com.example.blogging.repository.CategoryRepository;
import com.example.blogging.repository.PostRepository;
import com.example.blogging.repository.UserReposiory;
import com.example.blogging.service.PostService;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserReposiory userReposiory;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    private Map<String, Long> likesMap;

    @Autowired
    private Map<String, Long> dislikesMap;

    @Value("${project-image}")
    String uploadDir;

    @Override
    public PostDto createPost(PostDto postDto, int userId, int categoryId) {
        Users user = userReposiory.findById(userId).orElseThrow(() -> new UserNotFound("User Not Found!!"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFound("User Not Found!!"));
        Post post = mapper.map(postDto, Post.class);
        post.setImageName("default.jpg");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        post = postRepository.save(post);
        return mapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, int id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post Not Found with id : " + id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setLikeCounts(postDto.getLikeCounts());
        post.setDislikeCounts(post.getDislikeCounts());
        if (!postDto.getImageName().isBlank()) {
            post.setImageName(postDto.getImageName());
        }
        post.setAddedDate(new Date());
        Category category = categoryRepository.findById(postDto.getCategory().getId())
                .orElseThrow(() -> new CategoryNotFound("Category not found"));
        post.setCategory(category);
        post = postRepository.save(post);
        return mapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(PostDto postDto) throws IOException {


        Post post = mapper.map(postDto, Post.class);

        postRepository.delete(post);


    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> page = postRepository.findAll(pageable);
        List<Post> posts = page.getContent();
        List<PostDto> postsDto = posts.stream().map(p -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContents(postsDto);
        postResponse.setPageSize(pageSize);
        postResponse.setPageNumber(pageNumber);
        postResponse.setTotalElements(page.getTotalElements());
        postResponse.setTotalPages(page.getTotalPages());
        postResponse.setLast(page.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(int postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post Not Found with id : " + postId));
        if(likesMap.containsKey(String.valueOf(postId))){
            long likeCounts=likesMap.get(String.valueOf(postId));
            post.setLikeCounts(likeCounts);
        }
        if(dislikesMap.containsKey(String.valueOf(postId))){
            long dislikeCounts= dislikesMap.get(String.valueOf(postId));
            post.setDislikeCounts(dislikeCounts);
        }
        PostDto dto = mapper.map(post, PostDto.class);
        return dto;
    }

    @Override
    public List<PostDto> getPostByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFound("Category not found"));
        List<Post> post = postRepository.findByCategory(category);
        return post.stream().map((p) -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
    }


    @Override
    public PostResponse getPostByUser(int userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//        Users user = userReposiory.findById(userId).orElseThrow(() -> new UserNotFound("User not found"));
        Page<Post> page = postRepository.findByUserId(userId, pageable);
        List<Post> posts = page.getContent();
        List<PostDto> postsDto = posts.stream().map(p -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContents(postsDto);
        postResponse.setPageSize(pageSize);
        postResponse.setPageNumber(pageNumber);
        postResponse.setTotalElements(page.getTotalElements());
        postResponse.setTotalPages(page.getTotalPages());
        postResponse.setLast(page.isLast());
        return postResponse;

    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleOrContentContains(keyword);
        List<PostDto> postDtos = posts.stream().map(p -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto uploadImage(String fileName, MultipartFile file, int id) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post Not Found Exception"));

        Path uploadPath = Paths.get(uploadDir + File.separator + id);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            String fileCode = RandomStringUtils.randomAlphanumeric(8);
            fileName = fileCode + "_" + fileName;
            post.setImageName(fileName);
            post = postRepository.save(post);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return mapper.map(post, PostDto.class);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    @Override
    public InputStream getImage(Integer id) throws FileNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post Not Found"));
        String fileName = post.getImageName();
        String fullPath = uploadDir + File.separator + id + File.separator + fileName;
        try {
            InputStream is = new FileInputStream(fullPath);
            return is;
        } catch (Exception e) {
            fullPath = uploadDir + File.separator + fileName;
            InputStream is = new FileInputStream(fullPath);
            return is;
        }


    }


    @Override
    public void storeLikeCounts(Integer postId, Long likeCounts,Long dislikeCounts) {
        likesMap.put(String.valueOf(postId),likeCounts);
        dislikesMap.put(String.valueOf(postId),dislikeCounts);
    }



    @Transactional
    @Scheduled(cron = "1 * * * * *")
    public void storeLikes(){
        if(!likesMap.isEmpty() || !dislikesMap.isEmpty()){
                    for (Map.Entry<String,Long> like: likesMap.entrySet()) {
                        int postId = Integer.parseInt(like.getKey());
                        long likeCounts = like.getValue();
                        PostDto post = getPostById(postId);
                        post.setLikeCounts(likeCounts);
                        post = updatePost(post, postId);
            }
            likesMap.clear();
        }

        if(!dislikesMap.isEmpty()){
            for (Map.Entry<String,Long> dislike: dislikesMap.entrySet()) {
                int postId = Integer.parseInt(dislike.getKey());
                long dislikeCounts = dislike.getValue();
                PostDto post = getPostById(postId);
                post.setDislikeCounts(dislikeCounts);
                post = updatePost(post, postId);
            }
            dislikesMap.clear();
        }

    }
}
