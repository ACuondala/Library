package com.example.nada.Services;

import com.example.nada.Dtos.UserDto.UserRequestDto;
import com.example.nada.Dtos.UserDto.UserResponseDto;
import com.example.nada.Mappers.UserMapper;
import com.example.nada.Models.User;
import com.example.nada.Repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
