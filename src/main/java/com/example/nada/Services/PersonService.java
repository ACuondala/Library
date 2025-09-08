package com.example.nada.Services;

import com.example.nada.Dtos.PersonDto.PersonDto;
import com.example.nada.Dtos.PersonDto.RegisterDto;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.RegisterPersonMapper;
import com.example.nada.Models.Person;
import com.example.nada.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RegisterPersonMapper registerPersonMapper;
    @Transactional(readOnly = true)
    public Page<PersonDto> getAll(Pageable pageable) {
        return this.personRepository.findAll(pageable).map(registerPersonMapper::toDto);

    }

    @Transactional(readOnly = true)
    public PersonDto show(UUID id) {
       Person persons=this.personRepository.findById(id)
               .orElseThrow(()-> new EntitiesNotFoundException("This entities id doesn't exist"));

       return this.registerPersonMapper.toDto(persons);
    }

    Transactional
    public PersonDto store(RegisterDto registerDto) {

    }
}
