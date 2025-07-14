package com.example.nada.Services;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.CategoryDto.CategoryRequestDto;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.CategoryMappers;
import com.example.nada.Models.Category;
import com.example.nada.Repositories.CategoryRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDto> getAllCategories(Pageable pageable){





        return this.categoryRepository.findAll(pageable).map(CategoryMappers::modelToDto);
    }

    @Transactional
    public CategoryDto saveCategory(CategoryRequestDto dto) {
        Category category= new Category();
        CategoryMappers.dtoToModel(category,dto);
        category=this.categoryRepository.save(category);

        return CategoryMappers.modelToDto(category);
    }

    @Transactional(readOnly = true)
    public CategoryDto show(UUID id) {
        Category category=this.categoryRepository.findById(id)
                .orElseThrow(()-> new EntitiesNotFoundException("This category doesn't exist"));
        return CategoryMappers.modelToDto(category);
    }

    @Transactional
    public CategoryDto update(CategoryRequestDto categoryDto, UUID id) {
        try{
            Category category=this.categoryRepository.getReferenceById(id);
            CategoryMappers.dtoToModel(category,categoryDto);
            category=this.categoryRepository.save(category);
            return CategoryMappers.modelToDto(category);
        }catch(EntitiesNotFoundException e){
            throw new EntitiesNotFoundException("This category doesn't exist");
        }

    }

    public void delete(UUID id){
        try{
            Category category=this.categoryRepository.getReferenceById(id);
            this.categoryRepository.delete(category);
        }catch(EntitiesNotFoundException e){
            throw new EntitiesNotFoundException("This category doesn't exist");
        } /*catch (DataIntegrityViolationException e) {

            throw new IntegityException("This insurance Company id: " + id + " has related child entities and cannot be deleted");
        }*/
    }
}
