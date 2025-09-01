package com.example.nada.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String cargo;

    private String earn;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
