package com.example.nada.Mappers;

import com.example.nada.Dtos.PersonDto.PersonDto;
import com.example.nada.Dtos.PersonDto.RegisterDto;
import com.example.nada.Dtos.PersonDto.UpdatePerson;
import com.example.nada.Models.Person;
import com.example.nada.Models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface RegisterPersonMapper {

    PersonDto toDto(Person person);

    Person toEntity(RegisterDto registerDto);

    User toUser(RegisterDto registerDto, Person person);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void UpdateEntityFromDto(@MappingTarget Person person, UpdatePerson updatePerson);
}
