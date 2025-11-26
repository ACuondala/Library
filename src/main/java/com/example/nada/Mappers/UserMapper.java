package com.example.nada.Mappers;

import com.example.nada.Dtos.UserDto.UserRequestDto;
import com.example.nada.Dtos.UserDto.UserResponseDto;
import com.example.nada.Dtos.UserDto.UserUpdateDto;
import com.example.nada.Models.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserUpdateDto dto, @MappingTarget User user);
}
