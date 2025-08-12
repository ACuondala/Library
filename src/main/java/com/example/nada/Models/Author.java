package com.example.nada.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String name;

    @Column(nullable = true)
    private String pseudonym;

    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String photo;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String biografia;

    @ManyToMany(mappedBy = "authors")
    private Set<Books> books= new HashSet<>();

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
}
