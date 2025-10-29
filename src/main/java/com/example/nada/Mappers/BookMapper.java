package com.example.nada.Mappers;

import com.example.nada.Dtos.BookDtos.BookRequestDto;
import com.example.nada.Dtos.BookDtos.BookResponseDto;
import com.example.nada.Models.Author;
import com.example.nada.Models.Book;
import com.example.nada.Models.Category;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    void updateBook(BookRequestDto dto, @MappingTarget Book book);

    
}
