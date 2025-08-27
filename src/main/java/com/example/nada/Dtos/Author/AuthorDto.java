package com.example.nada.Dtos.Author;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuthorDto(
         UUID id,
        String name,
        LocalDateTime createdAt
) {}
