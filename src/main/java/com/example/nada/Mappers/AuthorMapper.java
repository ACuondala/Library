package com.example.nada.Mappers;

import com.example.nada.Dtos.AuthorDtos.AuthorRequestDto;
import com.example.nada.Dtos.AuthorDtos.AuthorResponseDto;
import com.example.nada.Dtos.CategoryDtos.CategoryRequestDto;
import com.example.nada.Models.Author;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponseDto toDto(Author author);

    Author toEntity(AuthorRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAuthor(AuthorRequestDto dto,@MappingTarget Author author);
}
