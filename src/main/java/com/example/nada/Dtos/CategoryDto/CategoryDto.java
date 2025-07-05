package com.example.nada.Dtos.CategoryDto;

import java.time.Instant;
import java.util.UUID;

public record CategoryDto(UUID id, String name, Instant createdAt) {

}
