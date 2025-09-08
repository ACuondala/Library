package com.example.nada.Mappers;

import com.example.nada.Dtos.PersonDto.PersonDto;
import com.example.nada.Dtos.PersonDto.RegisterDto;
import com.example.nada.Models.Person;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface RegisterPersonMapper {

    PersonDto toDto(Person person);

    Person toEntity(RegisterDto registerDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void UpdateEntityFromDto(Person person,RegisterDto registerDto);
}
