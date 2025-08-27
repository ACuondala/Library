package com.example.nada.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Books {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String isbn;

    private String title;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(nullable = true)
    private String formate;

    @Column(name = "number_of_page")
    private Integer numberOfPage;

    private Integer qtd;

    @Column(name="qtd_available")
    private Integer qtdAvailable;

    @ManyToMany
    @JoinTable(name="author_books", // middle table
                joinColumns=@JoinColumn(name="book_id"), // must be id of this class
                inverseJoinColumns=@JoinColumn(name="author_id") // must be id of other class
             )
    private Set<Author> authors= new HashSet<>();

    @ManyToMany
    @JoinTable(name="book_publisher", //middle table
                joinColumns=@JoinColumn(name="book_id"), // must be id of this class
                inverseJoinColumns = @JoinColumn(name="publisher_id") // this must be the id of ohter class
    )
    private Set<Publisher> publishers= new HashSet<>();

    @ManyToMany
    @JoinTable(name="book_category", //middle table
                joinColumns=@JoinColumn(name = "book_id"), // must be id of this class
                inverseJoinColumns=@JoinColumn(name = "category_id") // this must be the id of ohter class
    )
    private Set<Category> categories= new HashSet<>();


    @Column(name="created_at")
    private LocalDateTime createdAt;


}
