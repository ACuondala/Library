package com.example.nada.Repositories;

import com.example.nada.Models.Studant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudantRepository extends JpaRepository<Studant, UUID> {
}
