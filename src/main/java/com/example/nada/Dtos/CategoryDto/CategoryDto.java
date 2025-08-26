package com.example.nada.Dtos.CategoryDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryDto(UUID id, String name, LocalDateTime createdAt) {

}
