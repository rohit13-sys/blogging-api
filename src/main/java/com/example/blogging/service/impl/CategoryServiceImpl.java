package com.example.blogging.service.impl;

import com.example.blogging.entity.Category;
import com.example.blogging.entity.Users;
import com.example.blogging.exceptions.CaregoryAlreadyExists;
import com.example.blogging.exceptions.CategoryNotFound;
import com.example.blogging.exceptions.UserNotFound;
import com.example.blogging.payloads.CategoryDto;
import com.example.blogging.repository.CategoryRepository;
import com.example.blogging.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto){
        try{
            CategoryDto savedCategory=getCategoryByCatgoryName(categoryDto.getCategoryTitle());
            if(savedCategory!=null){
                throw new CaregoryAlreadyExists("Category Already Exists!!");
            }else {
                throw new CategoryNotFound("Category Not Found!!!");
            }
        }catch (CategoryNotFound e){
            Category category=dtoToCategory(categoryDto);
            categoryRepository.save(category);
            return categoryToDto(category);
        }

    }




    @Override
    public CategoryDto updateCategory(int id, CategoryDto categoryDto) {
        Category category=categoryRepository.findById(id).get();
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        categoryRepository.save(category);
        return categoryToDto(category);
    }

    @Override
    public CategoryDto getCategoryByCatgoryName(String categoryName) {

        Category category=categoryRepository.findByCategoryTitle(categoryName);
        if(category!=null){

            return categoryToDto(category);

        }else {
            throw new CategoryNotFound("Category Not Found!!!");
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        return categories.stream().map(this::categoryToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(int id) {
      categoryRepository.deleteById(id);
    }

    @Override
    public int getCategoryIdByCategoryName(String categoryName) {
        Category category=categoryRepository.findByCategoryTitle(categoryName);
        if (category != null) {
            return category.getId();
        }else {
            throw new UserNotFound("User Not Found!!! ");
        }
    }


    private  Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto,Category.class);
    }

    private CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
