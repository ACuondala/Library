package com.example.nada.Security.User;

import com.example.nada.Models.User;
import com.example.nada.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username).orElseThrow(
                ()-> new EntityNotFoundException("this user doesn't exist"));
        return new UserDetail(
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_"+user.getRoles()))

        );
    }
}
