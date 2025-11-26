package com.example.nada.Services;

import com.example.nada.Dtos.UserDto.UserRequestDto;
import com.example.nada.Dtos.UserDto.UserResponseDto;
import com.example.nada.Dtos.UserDto.UserUpdateDto;
import com.example.nada.Mappers.UserMapper;
import com.example.nada.Models.User;
import com.example.nada.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.passwordEncoder=passwordEncoder;
    }

    public Page<UserResponseDto> getAll(Pageable pageable) {

        return this.userRepository.findAll(pageable).map(this.userMapper::toDto);
    }

    public UserResponseDto store(UserRequestDto dto) {
        if(this.userRepository.existsByEmail(dto.email())){
            throw new RuntimeException("this email is associate with a user");
        }
        User user =this.userMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.password()));

        this.userRepository.save(user);
        return this.userMapper.toDto(user);
    }

    public UserResponseDto show(UUID id) {

        User user=this.userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("this user: "+id+" doesn't exist"));

        return this.userMapper.toDto(user);
    }

    public UserResponseDto update(UserUpdateDto dto, UUID id) {
        User user= this.userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("this user id: "+id+" doesn't exist"));
        this.userMapper.updateUser(dto,user);
        this.userRepository.save(user);
        return this.userMapper.toDto(user);
    }

    public void delete(UUID id) {
        User user= this.userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("this user id: "+id+" doesn't exist"));
        this.userRepository.delete(user);
    }
}
