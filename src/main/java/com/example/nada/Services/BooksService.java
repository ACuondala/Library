package com.example.nada.Services;

import com.example.nada.Dtos.Books.BookRequestDto;
import com.example.nada.Dtos.Books.BooksDto;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.BookMapper;
import com.example.nada.Models.Author;
import com.example.nada.Models.Books;
import com.example.nada.Models.Publisher;
import com.example.nada.Repositories.AuthorRepository;
import com.example.nada.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BooksService {
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private   BookMapper bookMapper;

    @Autowired
    private  AuthorRepository authorRepository;


    public BooksService(BookRepository bookRepository,BookMapper bookMapper){
        this.bookRepository=bookRepository;
        this.bookMapper=bookMapper;
    }

    @Transactional(readOnly = true)
    public Page<BooksDto> getAll(Pageable pageable) {
        return this.bookRepository.findAll(pageable).map(this.bookMapper::toDto);
    }
    @Transactional
    public BooksDto show(UUID id) {
        Books books= this.bookRepository.findById(id).orElseThrow(()->new EntitiesNotFoundException("this id doesn't exist"));
        return this.bookMapper.toDto(books);
    }

    public BooksDto create(BookRequestDto bookRequestDto) {
        Books books=this.bookMapper.toModel(bookRequestDto);

        Set<Author> authors= new TreeSet<>();
        if(bookRequestDto.authors() != null){
            for(UUID id: bookRequestDto.authors()){
                authors.add(this.getorCreateAuthor(id,null));
            }

        }
        if(bookRequestDto.authorName()!= null && bookRequestDto.authorName().isEmpty()){
            authors.addAll(
                    bookRequestDto.authorName().stream()
                            .filter(name -> name != null && !name.isBlank()) // ignora nulos/vazios
                            .map(name -> this.getorCreateAuthor(null, name))
                            .collect(Collectors.toSet())
            );
        }

        books.setAuthors(authors);

        return this.bookMapper.toDto(books);


    }

    private Author getorCreateAuthor(UUID id, String name){
        if(id != null){
            return this.authorRepository.findById(id)
                    .orElseThrow(()-> new EntitiesNotFoundException("This author id doesn't exist"));
        }
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("the field must be filled");
        }

        String normalizedName =this.normalizeName(name);

        return this.authorRepository.findByName(normalizedName)
                .orElseGet(()->{
                    Author newAuthor= new Author();
                    newAuthor.setName(normalizedName);
                    return this.authorRepository.save(newAuthor);
                });
    }

    private String normalizeName(String name) {
        return Arrays.stream(name.trim().toLowerCase().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
