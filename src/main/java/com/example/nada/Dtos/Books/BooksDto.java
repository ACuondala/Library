package com.example.nada.Dtos.Books;

import com.example.nada.Controllers.AuthorController;
import com.example.nada.Dtos.Author.AuthorDto;
import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.PublisherDto.PublisherDto;
import com.example.nada.Models.Category;
import com.example.nada.Models.Author;
import com.example.nada.Models.Publisher;
import java.time.LocalDate;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.Set;

public record BooksDto(
        UUID id,
        String isbn,
        String title,
        LocalDate publishDate,
        String formate,
        Integer numberOfPage,
        Set<AuthorDto> authors,
        Integer qtd,
        Integer qtdAvailable,
        Set<PublisherDto> publishers,
        Set<CategoryDto> categories,
        LocalDateTime createdAt
) {}