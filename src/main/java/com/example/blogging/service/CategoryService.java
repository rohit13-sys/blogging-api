package com.example.blogging.service;

import com.example.blogging.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {



    CategoryDto createCategory(CategoryDto categoryDto);


    CategoryDto updateCategory(String id, CategoryDto categoryDto);


    CategoryDto getCategoryByCatgoryName(String categoryName);


    List<CategoryDto> getAllCategories();


    void deleteCategory(String id);


    String getCategoryIdByCategoryName(String categoryName);
}
