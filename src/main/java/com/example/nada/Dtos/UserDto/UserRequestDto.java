package com.example.nada.Dtos.UserDto;

import com.example.nada.Enum.UserStatus;

import java.time.LocalDateTime;

public record UserRequestDto(
        String name,
        String email,
        String password


) {
}
