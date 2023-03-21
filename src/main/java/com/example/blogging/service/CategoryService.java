package com.example.blogging.service;

import com.example.blogging.payloads.CategoryDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CategoryService {


    @CacheEvict(cacheNames = {"UserCache"},allEntries = true)
    CategoryDto createCategory(CategoryDto categoryDto);

    @CacheEvict(cacheNames = {"CategoryCache"}, key = "#id" ,allEntries = true)
    CategoryDto updateCategory(int id,CategoryDto categoryDto);

    @CacheEvict(cacheNames = {"CategoryCache"}, key = "#categoryName" ,allEntries = true)
    CategoryDto getCategoryByCatgoryName(String categoryName);

    @Cacheable(cacheNames = {"CategoryCache"})
    List<CategoryDto> getAllCategories();

    @CacheEvict(cacheNames = {"CategoryCache"}, key = "#id" ,allEntries = true)
    void deleteCategory(int id);

    @Cacheable(cacheNames = {"CategoryCache"}, key = "#id", unless = "#result==null")
    int getCategoryIdByCategoryName(String categoryName);
}
