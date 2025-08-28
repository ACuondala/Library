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
        Set<String> authorNames,       // nomes para novos autores
        Set<String> publisherNames,    // nomes para novos publishers
        Set<String> categoryNames,     // nomes para novas categorias
        @NotNull LocalDate publishDate,
        @NotBlank String formate,
        @NotNull @Min(1) Integer numberOfPage,
        Set<UUID> authorIds,           // ids de autores já existentes
        Set<UUID> publisherIds,        // ids de publishers já existentes
        Set<UUID> categoryIds,         // ids de categorias já existentes
        @NotNull @Min(1) Integer qtd,
        @NotNull @Min(0) Integer qtdAvailable
) {
}