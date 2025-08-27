package com.example.nada.Dtos.PublisherDto;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record PublisherDto(
        UUID id,
        String name,
        LocalDateTime createdAt) {
}
