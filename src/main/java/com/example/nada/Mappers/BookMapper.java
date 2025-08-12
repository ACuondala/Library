package com.example.nada.Mappers;

import com.example.nada.Dtos.BookDto.BookDto;
import com.example.nada.Dtos.BookDto.BookRequestDto;
import com.example.nada.Models.Books;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Books book);

    Books toModel(BookRequestDto bookRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void UpadateEntityFromDto(BookRequestDto bookRequestDto, @MappingTarget Books books);

}
