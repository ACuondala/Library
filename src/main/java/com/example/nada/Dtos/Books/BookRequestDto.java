package com.example.nada.Dtos.Books;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record BookRequestDto(
        @NotBlank String isbn,
        @NotBlank String title,
        Set<String> authorName,
        Set<String> publisherNames,
        Set<String> categoryName,
        @NotNull LocalDate publishDate,
        @NotBlank String formate,
        @NotNull @Min(1) Integer numberOfPage,
        Set<UUID> authors,
        @NotNull Set<UUID> publishers,
        @NotNull Set<UUID> categories,
        @NotNull @Min(1) Integer qtd,
        @NotNull @Min(0) Integer qtdAvailable
) {
}
