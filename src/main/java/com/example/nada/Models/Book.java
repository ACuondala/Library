package com.example.nada.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    private String title;
    private String isbn;
    private Integer yearPublish;
    @Column(name="total_quantity")
    private Integer totalQuantity;
    @Column(name="quantity_available")
    private Integer quantityAvailable;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "author_books",
            joinColumns=@JoinColumn(name = "book_id"),
            inverseJoinColumns=@JoinColumn(name="author_id")
    )
    private List<Author> authors= new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
