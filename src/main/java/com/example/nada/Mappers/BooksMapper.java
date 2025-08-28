package com.example.nada.Mappers;

import com.example.nada.Dtos.Author.AuthorDto;
import com.example.nada.Dtos.Books.BooksDto;
import com.example.nada.Dtos.Books.BookRequestDto;
import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.PublisherDto.PublisherDto;
import com.example.nada.Models.Author;
import com.example.nada.Models.Books;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;
import org.mapstruct.*;


@Mapper(componentModel="spring")
public interface BooksMapper {


    BooksDto toDto(Books book);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "publishers", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Books toModel(BookRequestDto bookRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "publishers", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void UpadateEntityFromDto(BookRequestDto bookRequestDto, @MappingTarget Books books);

    AuthorDto toDto(Author author);
    CategoryDto toDto(Category category);
    PublisherDto toDto(Publisher publisher);

}
