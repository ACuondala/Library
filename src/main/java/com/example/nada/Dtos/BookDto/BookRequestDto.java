package com.example.nada.Dtos.BookDto;

import com.example.nada.Models.Author;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record BookRequestDto(
        @NotBlank String isbn,
        @NotBlank String title,
        String subTitle,
        String authorName,
        @NotNull LocalDate publishDate,
        @NotBlank String formate,
        @NotNull @Min(1) Integer numberOfPage,
         UUID authors,
        @NotNull UUID publishers,
        @NotNull Set<UUID> categories,
               @NotNull @Min(1) Integer qtd,
        @NotNull @Min(0) Integer qtdAvailable
) {
}
