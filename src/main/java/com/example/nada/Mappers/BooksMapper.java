package com.example.nada.Mappers;

import com.example.nada.Dtos.Author.AuthorDto;
import com.example.nada.Dtos.Books.BookRequestDto;
import com.example.nada.Dtos.Books.BooksDto;
import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.PublisherDto.PublisherDto;
import com.example.nada.Models.Author;
import com.example.nada.Models.Books;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BooksMapper {

    // Converter entidade para DTO
    BooksDto toDto(Books book);

    // Converter DTO para entidade (ignorando as coleções)

    Books toModel(BookRequestDto bookRequestDto);

    // Atualizar entidade existente a partir do DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    void UpadateEntityFromDto(BookRequestDto bookRequestDto, @MappingTarget Books books);

    // Mapear outras entidades para DTOs
    AuthorDto toDto(Author author);
    CategoryDto toDto(Category category);
    PublisherDto toDto(Publisher publisher);
}
