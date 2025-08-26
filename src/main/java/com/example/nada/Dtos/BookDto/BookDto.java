package com.example.nada.Dtos.BookDto;

import com.example.nada.Models.Author;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record BookDto(
        UUID id,
     String isbn,
     String title,
     String subTitle,
     LocalDate publishDate,
     String formate,
     Integer numberOfPage,
     Set<Author> authors,
        Integer qtd,
        Integer qtdAvailable,
     Set<Publisher> publishers,
     Set<Category> categories,
        LocalDateTime createdAt
) {
}
