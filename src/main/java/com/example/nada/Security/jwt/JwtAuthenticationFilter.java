package com.example.nada.Security.jwt;

import com.example.nada.Security.User.UserDetail;
import com.example.nada.Security.User.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailService userDetailService;


    public JwtAuthenticationFilter(UserDetailService userDetailService, JwtUtils jwtUtils){
        this.jwtUtils=jwtUtils;
        this.userDetailService=userDetailService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader= request.getHeader("Authorization");
        String token= null;
        String username= null;


        if(authHeader != null || authHeader.startsWith("Bearer ")){

            token=authHeader.substring(7);
            if(jwtUtils.validateToken(token)){
                username= jwtUtils.extractUsername(token);
            }
        }

        if(username != null || SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetail userDetail = UserDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken=
                    new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request,response);
    }


}
