package com.example.blogging.repository;

import com.example.blogging.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {


    Category findByCategoryTitle(String categoryName);
}
