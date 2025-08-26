package com.example.nada.Mappers;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.CategoryDto.CategoryRequestDto;
import com.example.nada.Models.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface CategoryMapper {

    //@Mapping(target = "createdAt", expression = "java(toInstant(category.getCreatedAt()))")
    CategoryDto toDto(Category category);

    @Mapping(target = "books", ignore = true) // 🔹 Ignorar para não bagunçar o name
    Category toEntity(CategoryRequestDto categoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "books", ignore = true) // 🔹 Ignorar para não bagunçar o name
    void updateEntityFromDto(CategoryRequestDto dto, @MappingTarget Category category);
}
