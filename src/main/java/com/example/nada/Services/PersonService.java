package com.example.nada.Services;

import com.example.nada.Dtos.PersonDto.PersonDto;
import com.example.nada.Dtos.PersonDto.RegisterDto;
import com.example.nada.Dtos.PersonDto.UpdatePerson;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.RegisterPersonMapper;
import com.example.nada.Models.Person;
import com.example.nada.Models.User;
import com.example.nada.Repositories.PersonRepository;
import com.example.nada.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterPersonMapper registerPersonMapper;

    @Autowired
    private  PasswordEncoder passwordEncoder;


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

    @Transactional
    public PersonDto store(RegisterDto registerDto) {

        Person person=this.registerPersonMapper.toEntity(registerDto);

        person=this.personRepository.save(person);

        User user=this.registerPersonMapper.toUser(registerDto,person);
        user.setPassword(this.passwordEncoder.encode(registerDto.password()));
        user=this.userRepository.save(user);

        return this.registerPersonMapper.toDto(person);


    }

    @Transactional
    public PersonDto update(UUID id, UpdatePerson updatePerson) {
        try{
            Person person=this.personRepository.getReferenceById(id);
            this.registerPersonMapper.UpdateEntityFromDto(person, updatePerson);
            person=this.personRepository.save(person);
            return this.registerPersonMapper.toDto(person);
        }catch(EntitiesNotFoundException e){
            throw new EntitiesNotFoundException("this id doesn't exits");
        }
    }

    @Transactional
    public void delete(UUID id) {
        try{
            Person person=this.personRepository.getReferenceById(id);
           this.personRepository.delete(person);

        }catch(EntitiesNotFoundException e){
            throw new EntitiesNotFoundException("this id doesn't exits");
        }
    }
}
