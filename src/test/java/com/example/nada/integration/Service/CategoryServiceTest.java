package com.example.nada.integration.Service;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Models.Category;
import com.example.nada.Repositories.CategoryRepository;
import com.example.nada.Services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    public Category category;

    @BeforeEach

    void setUp(){
         this.category= new Category();
        this.category.setName("Romance");
    }

    @Test
    @DisplayName("should return all categories")
    void shouldListAllCategories() throws Exception{
        /*PageRequest pageable=PageRequest.of(0,10);
        Page<Category> categories= new PageImpl<>(List.of(this.category));

        when(repository.findAll(pageable)).thenReturn(categories);

        Page<CategoryDto> result= service.getAllCategories(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).name()).isEqualTo("Romance");
        verify(repository, times(1)).findAll(pageable);*/

    }
}
