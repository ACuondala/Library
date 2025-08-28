package com.example.nada.integration.Service;

import com.example.nada.Dtos.Books.BookRequestDto;
import com.example.nada.Dtos.Books.BooksDto;
import com.example.nada.Models.Author;
import com.example.nada.Models.Books;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;
import com.example.nada.Repositories.AuthorRepository;
import com.example.nada.Repositories.BookRepository;
import com.example.nada.Repositories.CategoryRepository;
import com.example.nada.Repositories.PublisherRespository;
import com.example.nada.Services.BooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BooksServiceTest {

    @Autowired
    private BooksService booksService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRespository publisherRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Author existingAuthor;
    private Publisher existingPublisher;
    private Category existingCategory;

    @BeforeEach
    void setUp() {
        // Create existing entities for testing
        existingAuthor = new Author();
        existingAuthor.setName("J.K. Rowling");
        existingAuthor = authorRepository.save(existingAuthor);

        existingPublisher = new Publisher();
        existingPublisher.setName("Bloomsbury");
        existingPublisher = publisherRepository.save(existingPublisher);

        existingCategory = new Category();
        existingCategory.setName("Fantasy");
        existingCategory = categoryRepository.save(existingCategory);
    }

    @Test
    @DisplayName("Should create book with new authors, publishers and categories")
    void shouldCreateBookWithNewRelationships() {
        // Given
        BookRequestDto bookRequest = new BookRequestDto(
                "978-0-439-70818-8",
                "Harry Potter and the Philosopher's Stone",
                Set.of("J.R.R. Tolkien", "George R.R. Martin"), // new authors
                Set.of("Penguin Random House", "HarperCollins"), // new publishers  
                Set.of("Adventure", "Magic"), // new categories
                LocalDate.of(1997, 6, 26),
                "Hardcover",
                223,
                null, // no existing author IDs
                null, // no existing publisher IDs
                null, // no existing category IDs
                100,
                90
        );

        // When
        BooksDto result = booksService.create(bookRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.isbn()).isEqualTo("978-0-439-70818-8");
        assertThat(result.title()).isEqualTo("Harry Potter and the Philosopher's Stone");

        // Verify the book was saved to database
        Books savedBook = bookRepository.findById(result.id()).orElse(null);
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getAuthors()).hasSize(2);
        assertThat(savedBook.getPublishers()).hasSize(2);
        assertThat(savedBook.getCategories()).hasSize(2);

        // Verify new authors were created
        assertThat(authorRepository.findByName("j.r.r. tolkien")).isPresent();
        assertThat(authorRepository.findByName("george r.r. martin")).isPresent();

        // Verify new publishers were created
        assertThat(publisherRepository.findByName("penguin random house")).isPresent();
        assertThat(publisherRepository.findByName("harpercollins")).isPresent();

        // Verify new categories were created
        assertThat(categoryRepository.findCategoryByName("adventure")).isPresent();
        assertThat(categoryRepository.findCategoryByName("magic")).isPresent();
    }

    @Test
    @DisplayName("Should create book with existing entities by ID")
    void shouldCreateBookWithExistingEntitiesById() {
        // Given
        BookRequestDto bookRequest = new BookRequestDto(
                "978-0-7475-3269-9",
                "Harry Potter Test Book",
                null, // no new author names
                null, // no new publisher names
                null, // no new category names
                LocalDate.of(2000, 1, 1),
                "Paperback",
                300,
                Set.of(existingAuthor.getId()), // existing author ID
                Set.of(existingPublisher.getId()), // existing publisher ID
                Set.of(existingCategory.getId()), // existing category ID
                50,
                45
        );

        // When
        BooksDto result = booksService.create(bookRequest);

        // Then
        assertThat(result).isNotNull();

        // Verify the book was saved with existing relationships
        Books savedBook = bookRepository.findById(result.id()).orElse(null);
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getAuthors()).hasSize(1);
        assertThat(savedBook.getPublishers()).hasSize(1);
        assertThat(savedBook.getCategories()).hasSize(1);

        // Verify it's using the existing entities
        assertThat(savedBook.getAuthors().iterator().next().getId()).isEqualTo(existingAuthor.getId());
        assertThat(savedBook.getPublishers().iterator().next().getId()).isEqualTo(existingPublisher.getId());
        assertThat(savedBook.getCategories().iterator().next().getId()).isEqualTo(existingCategory.getId());
    }

    @Test
    @DisplayName("Should create book with mixed new and existing entities")
    void shouldCreateBookWithMixedEntities() {
        // Given
        BookRequestDto bookRequest = new BookRequestDto(
                "978-1-234-56789-0",
                "Mixed Relationships Book",
                Set.of("New Author"), // new author
                Set.of("New Publisher"), // new publisher
                Set.of("New Category"), // new category
                LocalDate.of(2023, 1, 1),
                "Ebook",
                250,
                Set.of(existingAuthor.getId()), // existing author ID
                Set.of(existingPublisher.getId()), // existing publisher ID
                Set.of(existingCategory.getId()), // existing category ID
                75,
                70
        );

        // When
        BooksDto result = booksService.create(bookRequest);

        // Then
        assertThat(result).isNotNull();

        // Verify the book was saved with both new and existing relationships
        Books savedBook = bookRepository.findById(result.id()).orElse(null);
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getAuthors()).hasSize(2); // 1 new + 1 existing
        assertThat(savedBook.getPublishers()).hasSize(2); // 1 new + 1 existing
        assertThat(savedBook.getCategories()).hasSize(2); // 1 new + 1 existing

        // Verify new entities were created
        assertThat(authorRepository.findByName("new author")).isPresent();
        assertThat(publisherRepository.findByName("new publisher")).isPresent();
        assertThat(categoryRepository.findCategoryByName("new category")).isPresent();
    }
}