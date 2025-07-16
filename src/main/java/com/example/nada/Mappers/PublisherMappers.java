package com.example.nada.Mappers;

import com.example.nada.Dtos.PublisherDto.PublisherDto;
import com.example.nada.Dtos.PublisherDto.PublisherRequestDto;
import com.example.nada.Models.Publisher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublisherMappers {

    PublisherDto toDto(Publisher publisher);

    Publisher toEntity(PublisherRequestDto publisherDto);

    List<PublisherDto> toDtoList(List<Publisher> publishers);
}
