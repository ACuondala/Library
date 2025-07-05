package com.example.nada.Mappers;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.CategoryDto.CategoryRequestDto;
import com.example.nada.Models.Category;

public class CategoryMappers {

    public static CategoryDto modelToDto(Category category){
        return new CategoryDto(category.getId(), category.getName(),category.getCreatedAt());
    }

    public static void dtoToModel(Category category, CategoryRequestDto dto){
        category.setName(dto.name());
    }
}
