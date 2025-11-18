package com.example.nada.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="permission_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns=@JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions= new HashSet<>();
}
