package com.example.nada.Mappers;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.CategoryDto.CategoryRequestDto;
import com.example.nada.Models.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel="spring")
public interface CategoryMapper {


    CategoryDto toDto(Category category);

    Category toEntity(CategoryRequestDto categoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CategoryRequestDto dto, @MappingTarget Category category);
}
