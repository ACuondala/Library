package com.example.nada.Services;

import com.example.nada.Dtos.Author.AuthorDto;
import com.example.nada.Mappers.AuthorMapper;
import com.example.nada.Models.Author;
import com.example.nada.Repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper){
        this.authorRepository=authorRepository;
        this.authorMapper=authorMapper;
    }

    @Transactional(readOnly = true)
    public Page<AuthorDto> getAll(Pageable pageable) {
        return this.authorRepository.findAll(pageable).map(authorMapper::toDto);

    }
}
