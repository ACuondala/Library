package com.example.nada.Dtos.CategoryDtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponseDto(UUID id, String name, LocalDateTime createdAt) {
}
