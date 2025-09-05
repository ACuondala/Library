package com.example.nada.Mappers;

import com.example.nada.Dtos.PersonDto.RegisterDto;
import com.example.nada.Models.Person;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface RegisterPersonMapper {


    Person toEntity(RegisterDto registerDto);
}
