package com.example.blogging.service.impl;

import com.example.blogging.entity.Category;
import com.example.blogging.exceptions.CaregoryAlreadyExists;
import com.example.blogging.exceptions.CategoryNotFound;
import com.example.blogging.exceptions.UserNotFound;
import com.example.blogging.payloads.CategoryDto;
import com.example.blogging.repository.CategoryRepository;
import com.example.blogging.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Map<String,CategoryDto> categoryMap;

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
    public CategoryDto updateCategory(String id, CategoryDto categoryDto) {
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
//        System.out.println("First time call for all category");
        List<CategoryDto> categories=new ArrayList<>();
        if(categoryMap.size()>0){
//            System.out.println("call to map for all category");
            for(Map.Entry<String,CategoryDto> category:categoryMap.entrySet()){
                categories.add(category.getValue());
            }
            return categories;
        }else{
//            System.out.println("call to DB for all category");
            categories=categoryRepository.findAll().stream().map(this::categoryToDto).collect(Collectors.toList());;
            return categories;
        }

    }

    @Override
    public void deleteCategory(String id) {
      categoryRepository.deleteById(id);
    }

    @Override
    public String getCategoryIdByCategoryName(String categoryName) {
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
