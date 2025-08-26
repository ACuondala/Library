package com.example.nada.Services;

import com.example.nada.Dtos.BookDto.BookDto;
import com.example.nada.Dtos.BookDto.BookRequestDto;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.BookMapper;
import com.example.nada.Models.Author;
import com.example.nada.Models.Books;
import com.example.nada.Models.Publisher;
import com.example.nada.Repositories.AuthorRepository;
import com.example.nada.Repositories.BookRepository;
import com.example.nada.Repositories.CategoryRepository;
import com.example.nada.Repositories.PublisherRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sun.tools.javac.util.List.collector;
import static java.util.stream.Nodes.collect;
import static org.hibernate.Hibernate.map;

@Service
public class BooksService {
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private   BookMapper bookMapper;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PublisherRespository publisherRespository;





    @Transactional(readOnly = true)
    public Page<BookDto> getAll(Pageable pageable) {
        return this.bookRepository.findAll(pageable).map(this.bookMapper::toDto);
    }
    @Transactional(readOnly = true)
    public BookDto show(UUID id) {
        Books books= this.bookRepository.findById(id).orElseThrow(()->new EntitiesNotFoundException("this id doesn't exist"));
        return this.bookMapper.toDto(books);
    }

    @Transactional
    public BookDto create(BookRequestDto bookRequestDto) {
        Author author=this.getOrCreateAuthor(bookRequestDto.authors(),bookRequestDto.authorName());
        Publisher publisher = this.publisherRespository.findById(bookRequestDto.publishers())
                .orElseThrow(() -> new IllegalArgumentException("Publisher não encontrado"));

        //Books books = new Books();

    }


    private Author getOrCreateAuthor(UUID id, String name){
        if(id != null){
            return this.authorRepository.findById(id)
                    .orElseThrow(()-> new EntitiesNotFoundException("this author id doesn't exist"));
        }

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("The author name isn't to be null");
        }
        String normalizedName = Arrays.stream(name.trim().toLowerCase().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));

        return this.authorRepository.findByName(normalizedName)
                .orElseGet(()->{
                    Author newAuthor= new Author();
                    newAuthor.setName(normalizedName);
                    return this.authorRepository.save(newAuthor);

                });


    }
}
