package com.example.nada.Services;

import com.example.nada.Dtos.BookDto.BookDto;
import com.example.nada.Mappers.BookMapper;
import com.example.nada.Repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final  BookMapper bookMapper;


    public BookService(BookRepository bookRepository,BookMapper bookMapper){
        this.bookRepository=bookRepository;
        this.bookMapper=bookMapper;
    }

    @Transactional(readOnly = true)
    public Page<BookDto> getAll(Pageable pageable) {
    }
}
