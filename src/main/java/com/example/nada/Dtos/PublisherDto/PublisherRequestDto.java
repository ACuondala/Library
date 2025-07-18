package com.example.nada.Dtos.PublisherDto;

import jakarta.validation.constraints.NotBlank;

public record PublisherRequestDto(
        @NotBlank(message = "{PublisherRequestDto.name.NotBlank}")
        String name,

                                  String description,
                                  String country,
                                  String email,
                                  String phone,
                                  String website,

                                  String foundedAt) {
}
