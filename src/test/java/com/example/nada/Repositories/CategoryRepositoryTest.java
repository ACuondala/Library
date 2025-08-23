package com.example.nada.Repositories;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.CategoryDto.CategoryRequestDto;
import com.example.nada.Models.Category;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository repository;


    @Autowired
    EntityManager entityManager;

    @DisplayName("should return category name Successfuly")
    @Test
    void ShouldfindCategoryByNameTest() {
        String name="Drama";
        CategoryRequestDto dto= new CategoryRequestDto("Drama");
        this.createCategory(dto);
        Optional<Category> category= this.repository.findCategoryByName(name);
        assertThat(category.isPresent()).isTrue();
    }


    private Category createCategory(CategoryRequestDto dto){
        Category category= new Category();
        category.setName(dto.name());
        this.entityManager.persist(category);
        return category;
    }
}