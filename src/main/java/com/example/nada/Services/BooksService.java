package com.example.nada.Services;

import com.example.nada.Dtos.BookDto.BookDto;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.BookMapper;
import com.example.nada.Models.Books;
import com.example.nada.Repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BooksService {

    private final BookRepository bookRepository;
    private final  BookMapper bookMapper;


    public BooksService(BookRepository bookRepository,BookMapper bookMapper){
        this.bookRepository=bookRepository;
        this.bookMapper=bookMapper;
    }

    @Transactional(readOnly = true)
    public Page<BookDto> getAll(Pageable pageable) {
        return this.bookRepository.findAll(pageable).map(this.bookMapper::toDto);
    }
    @Transactional(readOnly = true)
    public BookDto show(UUID id) {
        Books books= this.bookRepository.findById(id).orElseThrow(()->new EntitiesNotFoundException("this id doesn't exist"));
        return this.bookMapper.toDto(books);
    }
}
