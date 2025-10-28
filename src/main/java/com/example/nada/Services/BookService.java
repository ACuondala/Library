package com.example.nada.Services;

import com.example.nada.Dtos.BookDtos.BookRequestDto;
import com.example.nada.Dtos.BookDtos.BookResponseDto;
import com.example.nada.Mappers.BookMapper;
import com.example.nada.Repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper){
        this.bookRepository=bookRepository;
        this.bookMapper=bookMapper;
    }

    public Page<BookResponseDto> getAll(Pageable pageable) {
    }

    public BookResponseDto store(BookRequestDto dto) {
    }

    public BookResponseDto show(UUID id) {
    }

    public BookResponseDto update(UUID id, BookRequestDto dto) {
    }

    public void delete(UUID id) {
    }
}
