package com.example.nada.Mappers;

import com.example.nada.Dtos.BookDtos.BookRequestDto;
import com.example.nada.Dtos.BookDtos.BookResponseDto;
import com.example.nada.Models.Book;
import com.example.nada.Models.Category;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring",
        uses={AuthorMapper.class,CategoryMapper.class, PublisherMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookMapper {


    BookResponseDto toDto(Book book);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    Book toEntity(BookRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBook(BookRequestDto dto, @MappingTarget Book book);

    default Category map(UUID id) {
        return null;
    }
}
