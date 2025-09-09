package com.example.nada.Dtos.PersonDto;

import com.example.nada.Enums.PersonStatus;

import java.time.LocalDate;

public record UpdatePerson(
        String name,

        String email,

        String phoneNumber,

        LocalDate birthDate,

        String gender,

        PersonStatus Status
) {
}
