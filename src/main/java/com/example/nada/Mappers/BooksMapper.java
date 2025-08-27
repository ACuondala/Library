package com.example.nada.Mappers;

import com.example.nada.Dtos.Books.BooksDto;
import com.example.nada.Dtos.Books.BookRequestDto;
import com.example.nada.Models.Books;
import org.mapstruct.*;


@Mapper(componentModel="spring")
public interface BooksMapper {

    BooksDto toDto(Books book);

    @Mapping(target = "authors", ignore = true) // vamos tratar manualmente
    @Mapping(target = "publishers", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Books toModel(BookRequestDto bookRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "publishers", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void UpadateEntityFromDto(BookRequestDto bookRequestDto, @MappingTarget Books books);

}
