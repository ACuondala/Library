package com.example.nada.Security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtUtils {
  // criação da chave e tempo de expiração
    private final String SECRET_KEY="Secret_key 02";
    private final long EXPIRATION_MS=1000*60*60;

    //pego a chave(Key)
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    //gerar o token

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.ES256)
                .compact();
    }


    //validar o token

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        }catch(JwtException e){
            return false;
        }
    }

    //Extrair o username

    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
