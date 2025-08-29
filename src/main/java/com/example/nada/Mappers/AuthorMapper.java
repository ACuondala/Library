package com.example.nada.Mappers;

import com.example.nada.Dtos.Author.AuthorDto;
import com.example.nada.Dtos.Author.AuthorRequestDto;
import com.example.nada.Models.Author;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);
    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "books", ignore = true)
    void updateEntytiFromDto(AuthorRequestDto dto, @MappingTarget Author author);
}
