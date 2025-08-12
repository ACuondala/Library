package com.example.nada.Services;

import com.example.nada.Dtos.Author.AuthorDto;
import com.example.nada.Dtos.Author.AuthorRequestDto;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.AuthorMapper;
import com.example.nada.Models.Author;
import com.example.nada.Repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    @Transactional
    public AuthorDto store(AuthorRequestDto authorRequestDto) {

        Author author=this.authorMapper.toEntity(authorRequestDto);
        Author authorSave=this.authorRepository.save(author);

        return this.authorMapper.toDto(authorSave);
    }

    @Transactional(readOnly = true)
    public AuthorDto show(UUID id) {
        Author author=this.authorRepository.findById(id).orElseThrow(
                ()-> new EntitiesNotFoundException("this author doesn't exist")
        );

        return this.authorMapper.toDto(author);
    }

    @Transactional
    public AuthorDto update(UUID id, AuthorRequestDto authorRequestDto) {
        try{
            Author author= this.authorRepository.getReferenceById(id);
            this.authorMapper.updateEntytiFromDto(authorRequestDto,author);
            author=this.authorRepository.save(author);
            return this.authorMapper.toDto(author);
        }catch(EntitiesNotFoundException e){
            throw new EntitiesNotFoundException("this author doesn't exist");
        }
    }
    @Transactional
    public void delete(UUID id) {

        try{
            Author author=this.authorRepository.getReferenceById(id);
            this.authorRepository.delete(author);
            
        }catch(EntitiesNotFoundException e){
            throw new EntitiesNotFoundException("this author doesn't exist\"");
        }
    }
}
