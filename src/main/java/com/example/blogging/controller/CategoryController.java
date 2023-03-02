package com.example.blogging.controller;

import com.example.blogging.payloads.CategoryDto;
import com.example.blogging.payloads.UserDto;
import com.example.blogging.service.CategoryService;
import com.example.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        categoryDto=categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/{category-name}")
    public ResponseEntity<Object> getCategoryByTitle(@PathVariable("category-name") String categoryName){
        CategoryDto categoryDto=new CategoryDto();

        categoryDto=categoryService.getCategoryByCatgoryName(categoryName);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);

    }

    @PutMapping("/update-category")
    public ResponseEntity<Object> updateCategoryByTitle(@Valid @RequestBody CategoryDto categoryDto){

        int id=categoryService.getCategoryIdByCategoryName(categoryDto.getCategoryTitle());
        categoryDto=categoryService.updateCategory(id,categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);

    }

    @DeleteMapping("/delete-category")
    public ResponseEntity<Object> deleteCategoryByTitle(@Valid @RequestBody CategoryDto categoryDto){

        int id=categoryService.getCategoryIdByCategoryName(categoryDto.getCategoryTitle());
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category : "+categoryDto.getCategoryTitle()+" is deleted successfully", HttpStatus.OK);

    }

    @GetMapping("/all-categories")
    public ResponseEntity<Object> getAllCategories(){
        List<CategoryDto> categoryDtoList=categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);
    }
}
