package com.example.nada.Dtos.PublisherDto;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

public record PublisherResponseDto(

        UUID id,
        String name,
        String country,
        LocalDateTime createdAt
) {
}
