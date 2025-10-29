package com.example.nada.Repositories;

import com.example.nada.Models.Author;
import com.example.nada.Models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, UUID> {

    Optional<Publisher> findByNameIgnoreCase(String name);
}
