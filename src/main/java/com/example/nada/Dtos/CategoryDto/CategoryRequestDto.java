package com.example.nada.Dtos.CategoryDto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
        @NotBlank(message = "{CategoryRequestDto.name.NotBlank}")
        String name
    ) {
}
