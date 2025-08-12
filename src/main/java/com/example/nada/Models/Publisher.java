package com.example.nada.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "publishers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;
    private String country;
    private String email;
    private String phone;
    private String website;
    @Column(name="founded_at")
    private String foundedAt;
    @Column(name="created_at")
    @CreationTimestamp
    public Instant createdAt;

    @ManyToMany(mappedBy = "publishers")
    public Set<Books> books= new HashSet<>();
}
