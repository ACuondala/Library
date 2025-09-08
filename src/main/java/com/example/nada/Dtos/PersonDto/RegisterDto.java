package com.example.nada.Dtos.PersonDto;

import com.example.nada.Enums.PersonStatus;
import com.example.nada.Enums.StudantStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public record RegisterDto(
     String name,

    String email,

     String phoneNumber,

     LocalDate birthDate,

     String gender,

     PersonStatus Status,

     String password






) {
}
