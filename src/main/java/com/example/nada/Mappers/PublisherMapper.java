package com.example.nada.Mappers;

import com.example.nada.Dtos.PublisherDto.PublisherRequestDto;
import com.example.nada.Dtos.PublisherDto.PublisherResponseDto;
import com.example.nada.Models.Publisher;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "Spring")
public interface PublisherMapper {

    PublisherResponseDto toDto(Publisher publisher);

    Publisher toEntity(PublisherRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePublisher(PublisherRequestDto dto, @MappingTarget Publisher publisher);
}
