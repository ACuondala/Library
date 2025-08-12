package com.example.nada.Services;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.CategoryDto.CategoryFilter;
import com.example.nada.Dtos.CategoryDto.CategoryRequestDto;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.CategoryMapper;
import com.example.nada.Models.Category;
import com.example.nada.Repositories.CategoryRepository;

import com.example.nada.Specification.CategorySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper){

        this.categoryRepository=categoryRepository;
        this.categoryMapper=categoryMapper;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDto> getAllCategories(CategoryFilter filter,Pageable pageable){
        return this.categoryRepository.findAll(CategorySpecification.filterBy(filter),pageable).map(categoryMapper::toDto);
    }

    @Transactional
    public CategoryDto saveCategory(CategoryRequestDto dto) {
        Category category= this.categoryMapper.toEntity(dto);

        Category save=this.categoryRepository.save(category);

        return this.categoryMapper.toDto(category);
    }

    @Transactional(readOnly = true)
    public CategoryDto show(UUID id) {
        Category category=this.categoryRepository.findById(id)
                .orElseThrow(()-> new EntitiesNotFoundException("This category doesn't exist"));
        return this.categoryMapper.toDto(category);
    }

    @Transactional
    public CategoryDto update(CategoryRequestDto categoryDto, UUID id) {
        try{
            Category category=this.categoryRepository.getReferenceById(id);
            this.categoryMapper.updateEntityFromDto(categoryDto,category);

            category=this.categoryRepository.save(category);
            return categoryMapper.toDto(category);
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
