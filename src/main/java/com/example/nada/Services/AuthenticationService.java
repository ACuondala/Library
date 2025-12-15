package com.example.nada.Services;

import com.example.nada.Dtos.AuthDto.LoginDto;
import com.example.nada.Security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String autenticate(LoginDto dto){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(),dto.password())
        );
        return jwtService.generateToken(authentication);
    }
}
