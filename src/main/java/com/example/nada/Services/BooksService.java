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
        authors=this.addAuthorsToSet(bookRequestDto.authorIds(), bookRequestDto.authorNames(), authors);

        books.setAuthors(authors);

        Set<Publisher> publishers= new HashSet<>();
        publishers=this.addPublishersToSet(bookRequestDto.publisherIds(), bookRequestDto.publisherNames(),publishers);


        books.setPublishers(publishers);

        Set<Category> categories= new HashSet<>();
        categories=this.addCategoriesToSet(bookRequestDto.categoryIds(), bookRequestDto.categoryNames(),categories);


        books.setCategories(categories);

        books= this.bookRepository.saveAndFlush(books);
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
        authors=this.addAuthorsToSet(bookRequestDto.authorIds(), bookRequestDto.authorNames(), authors);

        // Handle publishers
        Set<Publisher> publishers = new HashSet<>();
        publishers=this.addPublishersToSet(bookRequestDto.publisherIds(), bookRequestDto.publisherNames(),publishers);

        // Handle categories
        Set<Category> categories = new HashSet<>();
        categories=this.addCategoriesToSet(bookRequestDto.categoryIds(), bookRequestDto.categoryNames(),categories);

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

    ////// Auxiliar functions for Authors ///////////////////////
    private Set<Author> addAuthorsToSet(Set<UUID> authorId, Set<String> authorName, Set<Author> authors){
        if(authorId != null){
            for(UUID id: authorId){
                authors.add(this.getOrCreateAuthor(id,null));
            }

        }

        if(authorName != null && !authorName.isEmpty()){
            authors.addAll(
                    authorName.stream()
                            .filter(name -> name != null && !name.isBlank()) // ignora nulos/vazios
                            .map(name -> this.getOrCreateAuthor(null, name))
                            .collect(Collectors.toSet())
            );
        }
        
        return authors;
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

    ////// Auxiliar functions for Publishers ///////////////////////
    private Set<Publisher> addPublishersToSet(Set<UUID> publisherId, Set<String> publisherName, Set<Publisher> publishers){
        if(publisherId != null){
            for(UUID id: publisherId){
                publishers.add(this.getOrCreatePublisher(id,null));
            }

        }

        if(publisherName != null && !publisherName.isEmpty()){
            publishers.addAll(
                    publisherName.stream()
                            .filter(name -> name != null && !name.isBlank()) // ignora nulos/vazios
                            .map(name -> this.getOrCreatePublisher(null, name))
                            .collect(Collectors.toSet())
            );
        }

        return publishers;
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

    ////// Auxiliar functions for Categories ///////////////////////
    private Set<Category> addCategoriesToSet(Set<UUID> categoryId, Set<String> categoryName, Set<Category> categories){
        if(categoryId != null){
            for(UUID id: categoryId){
                categories.add(this.getOrCreateCategory(id,null));
            }

        }

        if(categoryName != null && !categoryName.isEmpty()){
            categories.addAll(
                    categoryName.stream()
                            .filter(name -> name != null && !name.isBlank()) // ignora nulos/vazios
                            .map(name -> this.getOrCreateCategory(null, name))
                            .collect(Collectors.toSet())
            );
        }

        return categories;
    }
    private Category getOrCreateCategory(UUID id, String name){
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
