package com.example.nada.Dtos.BookDtos;

import com.example.nada.Dtos.AuthorDtos.AuthorResponseDto;
import com.example.nada.Models.Author;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record BookResponseDto(
         UUID id,
         String title,
         String isbn,
         Integer yearPublish,
         Integer totalQuantity,
         Integer quantityAvailable,
         Category category,
         List<AuthorResponseDto> authors,
         Publisher publisher,
         LocalDateTime createdAt
) {
}
