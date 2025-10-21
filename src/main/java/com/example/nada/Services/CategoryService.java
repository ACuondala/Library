package com.example.nada.Services;

import com.example.nada.Dtos.CategoryDtos.CategoryRequestDto;
import com.example.nada.Dtos.CategoryDtos.CategoryResponseDto;
import com.example.nada.Mappers.CategoryMapper;
import com.example.nada.Models.Category;
import com.example.nada.Repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper){
        this.categoryRepository=repository;
        this.categoryMapper=mapper;
    }

    @Transactional
    public CategoryResponseDto store(CategoryRequestDto categoryRequestDto) {

        Category category=this.categoryMapper.toEntity(categoryRequestDto);
        category=this.categoryRepository.save(category);
        return this.categoryMapper.toDto(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getAll(Pageable pageable) {
        return this.categoryRepository.findAll(pageable).map(this.categoryMapper::toDto);
    }


    @Transactional(readOnly = true)
    public CategoryResponseDto show(UUID id) {
        Category category= this.categoryRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("This category id: "+id+" doesn't exist"));

        return this.categoryMapper.toDto(category);
    }

    @Transactional
    public CategoryResponseDto update(CategoryRequestDto dto, UUID id) {

        try{

            Category category= this.categoryRepository.getReferenceById(id);

            this.categoryMapper.updateCategory(dto,category);
            category=this.categoryRepository.save(category);

            return this.categoryMapper.toDto(category);
        }catch(RuntimeException e){

        throw new EntityNotFoundException("this category id:"+id+" doesn't exist");

        }
    }

    @Transactional
    public void delete(UUID id) {
        Category category=this.categoryRepository.findById(id).
                orElseThrow(()->new EntityNotFoundException("this category id: "+id+" doesn't exist"));

        this.categoryRepository.delete(category);
    }
}
