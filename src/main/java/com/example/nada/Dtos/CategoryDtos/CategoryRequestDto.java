package com.example.nada.Dtos.CategoryDtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
        @NotBlank(message = "{category.name.notBlank}")
        String name) {
}
