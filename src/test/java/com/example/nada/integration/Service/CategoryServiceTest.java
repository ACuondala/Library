package com.example.nada.integration.Service;

import com.example.nada.Repositories.CategoryRepository;
import com.example.nada.Services.CategoryService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    void shouldListAllCategories(){

    }
}
