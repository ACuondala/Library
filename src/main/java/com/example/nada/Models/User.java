package com.example.nada.Models;

import com.example.nada.Enums.KindofUser;
import com.example.nada.Enums.PersonStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Column(name = "username")
    private String userName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private KindofUser kindofUser;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PersonStatus Status;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
