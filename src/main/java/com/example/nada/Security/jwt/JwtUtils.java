package com.example.nada.Security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtUtils {

    // criamos a chave secreta para assinar o token
    //criamos o tempo de duração do token
    private final String SECRET_KEY="Exemple_*97@_key";
    private final long EXPIRATION_MS= 1000*60*60;

    // método de assinatura

    private Key signingKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // gerar Token

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(signingKey(),SignatureAlgorithm.ES256)
                .compact();
    }

    //validar o token

    public boolean validateToken(String token){
        try{
             Jwts.parserBuilder()
                    .setSigningKey(signingKey())
                    .build()
                    .parseClaimsJws(token);
                    return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token não suportado");
        } catch (MalformedJwtException e) {
            System.out.println("Token mal formado");
        }  catch (IllegalArgumentException e) {
            System.out.println("Token vazio");
        }

        return false;
    }


    //Extrair o user do Token
    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
