package com.example.nada.Mappers;

import com.example.nada.Dtos.CategoryDtos.CategoryRequestDto;
import com.example.nada.Dtos.CategoryDtos.CategoryResponseDto;
import com.example.nada.Models.Category;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDto toDto(Category category);

    Category toEntity(CategoryRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategory(CategoryRequestDto categoryRequestDto,@MappingTarget Category category);
}
