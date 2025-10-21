package com.example.nada.Services;

import com.example.nada.Dtos.AuthorDtos.AuthorRequestDto;
import com.example.nada.Dtos.AuthorDtos.AuthorResponseDto;
import com.example.nada.Mappers.AuthorMapper;
import com.example.nada.Models.Author;
import com.example.nada.Repositories.AuthorRepository;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class AuthorService {


    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper){
        this.authorRepository=authorRepository;
        this.authorMapper=authorMapper;

    }

    @Transactional(readOnly = true)
    public Page<AuthorResponseDto> getAll(Pageable pageable) {
        return this.authorRepository.findAll(pageable).map(this.authorMapper::toDto);
    }

    @Transactional
    public AuthorResponseDto store(AuthorRequestDto dto) {
        Author author=this.authorMapper.toEntity(dto);
        return this.authorMapper.toDto(this.authorRepository.save(author));
    }

    @Transactional(readOnly = true)
    public AuthorResponseDto show(UUID id) {
        Author author=this.authorRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("this author id: "+id+" doesn't exist"));

        return this.authorMapper.toDto(author);
    }

    @Transactional
    public AuthorResponseDto update(UUID id, AuthorRequestDto dto) {
        try{
            Author author=this.authorRepository.getReferenceById(id);
            this.authorMapper.updateAuthor(dto,author);

            return this.authorMapper.toDto(this.authorRepository.save(author));
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("this author id: "+id+" doesn't exist");
        }
    }

    @Transactional
    public void delete(UUID id) {
        try{
            Author author=this.authorRepository.getReferenceById(id);
            this.authorRepository.delete(author);


        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("this author id: "+id+" doesn't exist");
        }
    }
}
