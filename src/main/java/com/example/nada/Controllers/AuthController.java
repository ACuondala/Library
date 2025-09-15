package com.example.nada.Controllers;

import com.example.nada.Dtos.AuthDto.LoginDto;
import com.example.nada.Dtos.AuthDto.LoginResponseDto;
import com.example.nada.Helpers.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Se chegou aqui, o usuário foi autenticado ✅
        String token = jwtUtils.generateToken(authentication.getName());

        return new LoginResponseDto(token);
    }
}

