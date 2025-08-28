package com.example.nada.Services;

import com.example.nada.Dtos.Books.BookRequestDto;
import com.example.nada.Dtos.Books.BooksDto;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.BooksMapper;
import com.example.nada.Models.Author;
import com.example.nada.Models.Books;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;
import com.example.nada.Repositories.AuthorRepository;
import com.example.nada.Repositories.BookRepository;

import com.example.nada.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nada.Helpers.NormalizedName;

import java.util.*;
import java.util.stream.Collectors;

import com.example.nada.Repositories.PublisherRespository;

@Service
public class BooksService {
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private BooksMapper bookMapper;

    @Autowired
    private  AuthorRepository authorRepository;

    @Autowired
    private  PublisherRespository publisherRespository;

    @Autowired
    private CategoryRepository categoryRepository;





    @Transactional(readOnly = true)
    public Page<BooksDto> getAll(Pageable pageable) {
        return this.bookRepository.findAll(pageable).map(this.bookMapper::toDto);
    }


    @Transactional(readOnly= true)
    public BooksDto show(UUID id) {
        Books books= this.bookRepository.findById(id).orElseThrow(()->new EntitiesNotFoundException("this id doesn't exist"));
        return this.bookMapper.toDto(books);
    }

    @Transactional
    public BooksDto create(BookRequestDto bookRequestDto) {
        Books books=this.bookMapper.toModel(bookRequestDto);

        Set<Author> authors= new HashSet<>();
        if(bookRequestDto.authorIds() != null){
            for(UUID id: bookRequestDto.authorIds()){
                authors.add(this.getOrCreateAuthor(id,null));
            }

        }
        if(bookRequestDto.authorNames()!= null && !bookRequestDto.authorNames().isEmpty()){
            authors.addAll(
                    bookRequestDto.authorNames().stream()
                            .filter(name -> name != null && !name.isBlank()) // ignora nulos/vazios
                            .map(name -> this.getOrCreateAuthor(null, name))
                            .collect(Collectors.toSet())
            );
        }

        books.setAuthors(authors);

        Set<Publisher> publishers= new HashSet<>();

        if(bookRequestDto.publisherIds() != null){
            for(UUID id: bookRequestDto.publisherIds()){
                publishers.add(this.getOrCreatePublisher(id, null));
            }
        }

        if(bookRequestDto.publisherNames() != null && !bookRequestDto.publisherNames().isEmpty()){

            publishers.addAll(bookRequestDto.publisherNames().stream()
                    .filter(name->name != null && !name.isBlank())
                    .map(name->this.getOrCreatePublisher(null, name))
                    .collect(Collectors.toSet())
            );
        }

        books.setPublishers(publishers);

        Set<Category> categories= new HashSet<>();

        if(bookRequestDto.categoryIds() != null){
            for(UUID id: bookRequestDto.categoryIds()){
                categories.add(this.getOrcreateCategory(id, null));
            }
        }

        if(bookRequestDto.categoryNames() != null && !bookRequestDto.categoryNames().isEmpty()){
            categories.addAll(bookRequestDto.categoryNames().stream()
                    .filter(name-> name != null && !name.isBlank())
                    .map(name->this.getOrcreateCategory(null,name))
                    .collect(Collectors.toSet())
            );
        }

        books.setCategories(categories);

        books= this.bookRepository.save(books);
        return this.bookMapper.toDto(books);
    }

    @Transactional
    public BooksDto update(UUID id, BookRequestDto bookRequestDto) {
        Books existingBook = this.bookRepository.findById(id)
                .orElseThrow(() -> new EntitiesNotFoundException("Book with id " + id + " doesn't exist"));

        // Update the existing book with new data
        this.bookMapper.UpadateEntityFromDto(bookRequestDto, existingBook);

        // Handle authors
        Set<Author> authors = new HashSet<>();
        if (bookRequestDto.authorIds() != null) {
            for (UUID authorId : bookRequestDto.authorIds()) {
                authors.add(this.getOrCreateAuthor(authorId, null));
            }
        }
        if (bookRequestDto.authorNames() != null && !bookRequestDto.authorNames().isEmpty()) {
            authors.addAll(
                    bookRequestDto.authorNames().stream()
                            .filter(name -> name != null && !name.isBlank())
                            .map(name -> this.getOrCreateAuthor(null, name))
                            .collect(Collectors.toSet())
            );
        }

        // Handle publishers
        Set<Publisher> publishers = new HashSet<>();
        if (bookRequestDto.publisherIds() != null) {
            for (UUID publisherId : bookRequestDto.publisherIds()) {
                publishers.add(this.getOrCreatePublisher(publisherId, null));
            }
        }
        if (bookRequestDto.publisherNames() != null && !bookRequestDto.publisherNames().isEmpty()) {
            publishers.addAll(bookRequestDto.publisherNames().stream()
                    .filter(name -> name != null && !name.isBlank())
                    .map(name -> this.getOrCreatePublisher(null, name))
                    .collect(Collectors.toSet())
            );
        }

        // Handle categories
        Set<Category> categories = new HashSet<>();
        if (bookRequestDto.categoryIds() != null) {
            for (UUID categoryId : bookRequestDto.categoryIds()) {
                categories.add(this.getOrcreateCategory(categoryId, null));
            }
        }
        if (bookRequestDto.categoryNames() != null && !bookRequestDto.categoryNames().isEmpty()) {
            categories.addAll(bookRequestDto.categoryNames().stream()
                    .filter(name -> name != null && !name.isBlank())
                    .map(name -> this.getOrcreateCategory(null, name))
                    .collect(Collectors.toSet())
            );
        }

        // Set relationships
        existingBook.setAuthors(authors);
        existingBook.setPublishers(publishers);
        existingBook.setCategories(categories);

        // Save the updated book
        existingBook = this.bookRepository.save(existingBook);
        return this.bookMapper.toDto(existingBook);
    }

    @Transactional
    public void delete(UUID id) {
        Books book = this.bookRepository.findById(id)
                .orElseThrow(() -> new EntitiesNotFoundException("Book with id " + id + " doesn't exist"));
        this.bookRepository.delete(book);
    }

    private Author getOrCreateAuthor(UUID id, String name){
        if(id != null){
            return this.authorRepository.findById(id)
                    .orElseThrow(()-> new EntitiesNotFoundException("This authors id doesn't exist"));
        }
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("the field must be filleds");
        }

        String normalizedName =NormalizedName.normalizeName(name);

        return this.authorRepository.findByName(normalizedName)
                .orElseGet(()->{
                    Author newAuthor= new Author();
                    newAuthor.setName(normalizedName);
                    return this.authorRepository.save(newAuthor);
                });
    }


    private Publisher getOrCreatePublisher(UUID id, String name){
        if(id != null){
            return this.publisherRespository.findById(id)
            .orElseThrow(()-> new EntitiesNotFoundException("This publisher id doesn't exist"));
        }
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("the field must be filleds");
        }

        String normalizedName =NormalizedName.normalizeName(name);

        return this.publisherRespository.findByName(normalizedName)
                .orElseGet(()->{
                    Publisher newPublisher= new Publisher();
                    newPublisher.setName(normalizedName);
                    return this.publisherRespository.save(newPublisher);
                });
    }

    private Category getOrcreateCategory(UUID id, String name){
        if(id != null){
            return this.categoryRepository.findById(id)
                    .orElseThrow(()-> new EntitiesNotFoundException("This publisher id doesn't exist"));
        }
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("the field must be filleds");
        }

        String normalizedName =NormalizedName.normalizeName(name);

        return this.categoryRepository.findCategoryByName(normalizedName)
                .orElseGet(()->{
                    Category newCategory= new Category();
                    newCategory.setName(normalizedName);
                    return this.categoryRepository.save(newCategory);
                });
    }


}
