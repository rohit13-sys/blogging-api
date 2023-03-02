package com.example.blogging.service;

import com.example.blogging.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(int id,CategoryDto categoryDto);

    CategoryDto getCategoryByCatgoryName(String categoryName);

    List<CategoryDto> getAllCategories();

    void deleteCategory(int id);

    int getCategoryIdByCategoryName(String categoryName);
}
