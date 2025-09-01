package com.example.nada.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name="teachers")
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    private String discipline;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
