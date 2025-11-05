package com.example.nada.Dtos.UserDto;

import com.example.nada.Enum.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        UserStatus status,
        LocalDateTime createdAt
) {
}
