package com.example.nada.Dtos.AuthorDtos;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuthorResponseDto(
        UUID id,

         String name,

        String nacionalidade,

        LocalDateTime createdAt
) {
}
