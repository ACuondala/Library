package com.example.nada.Mappers;

import com.example.nada.Dtos.PublisherDto.PublisherDto;
import com.example.nada.Dtos.PublisherDto.PublisherRequestDto;
import com.example.nada.Models.Publisher;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublisherMappers {

    PublisherDto toDto(Publisher publisher);

    Publisher toEntity(PublisherRequestDto publisherDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void updateEntityFromDto(PublisherRequestDto dto, @MappingTarget Publisher publisher);
}
