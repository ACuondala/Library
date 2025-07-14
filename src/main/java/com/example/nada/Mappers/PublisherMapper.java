package com.example.nada.Mappers;

import com.example.nada.Dtos.PublisherDto.PublisherDto;
import com.example.nada.Dtos.PublisherDto.PublisherRequestDto;
import com.example.nada.Models.Publisher;

public class PublisherMapper {


    public static PublisherDto modelToDto(Publisher publisher){
        return new PublisherDto(
                publisher.getId(),
                publisher.getName(),
                publisher.getDescription(),
                publisher.getCountry(),
                publisher.getEmail(),
                publisher.getPhone(),
                publisher.getWebsite(),
                publisher.getFoundedAt(),
                publisher.getCreatedAt()
        );
    }


    public static void dtoToModel(Publisher publisher, PublisherRequestDto dto){
        publisher.setName(dto.name()),
                publisher.setDescription(dto.description()),
        publisher.setCountry(dto.country()),
        publisher.setEmail(dto.email()),
        publisher.setPhone(dto.phone()),
        publisher.setWebsite(dto.website()),
        publisher.setFoundedAt(dto.foundedAt()),
    }
}
