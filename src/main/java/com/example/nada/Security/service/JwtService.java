package com.example.nada.Security.service;

import com.example.nada.Security.model.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtEncoder encoder;

    public JwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication){
        Instant now= Instant.now();
        Long expiry=3600L;

        String scopes=authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var claims= JwtClaimsSet.builder()
                .issuer("spring-securyti-jwt")
                .issuedAt(Instant.now()) // quando foi criado o token
                .expiresAt(now.plusSeconds(expiry)) //quando expira
                .subject(authentication.getName()) // pegar o email do user
                .claim("scopes",scopes) //passando os perfis do user
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
