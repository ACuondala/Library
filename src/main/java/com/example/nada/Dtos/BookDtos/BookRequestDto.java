package com.example.nada.Dtos.BookDtos;

import com.example.nada.Models.Author;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;

import java.util.List;
import java.util.UUID;

public record BookRequestDto(

        String title,
        String isbn,
        Integer yearPublish,
        Integer totalQuantity,
        Integer quantityAvailable,
        UUID category,
        List<UUID> authors,
        UUID publisher
) {
}
