package com.example.nada.Dtos.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorRequestDto(
        UUID id,


        String name,


        String pseudonym,

        String email,
        String phone,

        LocalDate dob,
        String photo,
        String biografia
) {
}
