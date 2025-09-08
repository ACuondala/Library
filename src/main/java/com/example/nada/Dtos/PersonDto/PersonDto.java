package com.example.nada.Dtos.PersonDto;

import com.example.nada.Enums.PersonStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PersonDto(
         UUID id,

          String name,

        String email,


        String phoneNumber,


        LocalDate birthDate,

        String gender,



        PersonStatus Status,


         LocalDateTime created_at
) {
}
