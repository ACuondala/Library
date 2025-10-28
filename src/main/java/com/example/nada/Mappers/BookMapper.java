package com.example.nada.Mappers;

import com.example.nada.Dtos.BookDtos.BookRequestDto;
import com.example.nada.Dtos.BookDtos.BookResponseDto;
import com.example.nada.Models.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses={AuthorMapper.class})
public interface BookMapper {

    @Mapping(target="authors",source = "authors")
    BookResponseDto toDto(Book book);

    @Mapping(target="category", ignore=true)
    @Mapping(target="publisher", ignore=true)
    @Mapping(target="authors", ignore = true)
    Book toEntity(BookRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBook(BookRequestDto dto, @MappingTarget Book book);
}
