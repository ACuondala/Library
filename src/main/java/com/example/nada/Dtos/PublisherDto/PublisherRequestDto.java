package com.example.nada.Dtos.PublisherDto;

public record PublisherRequestDto(String name,

                                  String description,
                                  String country,
                                  String email,
                                  String phone,
                                  String website,

                                  String foundedAt) {
}
