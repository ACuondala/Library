package com.example.nada.Dtos.PublisherDto;

import jakarta.validation.constraints.NotBlank;

public record PublisherRequestDto(
        @NotBlank(message = "{PublisherRequestDto.name.NotBlank}")
        String name) {}
