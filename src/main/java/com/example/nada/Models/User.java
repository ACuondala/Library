package com.example.nada.Models;

import com.example.nada.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @CreationTimestamp
    @Column(name="created_At")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="role_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles= new HashSet<>();

}
