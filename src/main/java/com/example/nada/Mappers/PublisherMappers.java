package com.example.nada.Mappers;

import com.example.nada.Dtos.PublisherDto.PublisherDto;
import com.example.nada.Dtos.PublisherDto.PublisherRequestDto;
import com.example.nada.Models.Publisher;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublisherMappers {

    PublisherDto toDto(Publisher publisher);
    @Mapping(target = "books", ignore = true)
    Publisher toEntity(PublisherRequestDto publisherDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "books", ignore = true)
   void updateEntityFromDto(PublisherRequestDto dto, @MappingTarget Publisher publisher);
}
