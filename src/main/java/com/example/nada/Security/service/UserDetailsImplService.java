package com.example.nada.Security.service;

import com.example.nada.Repositories.UserRepository;
import com.example.nada.Security.model.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImplService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImplService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).map(UserDetailsImpl::new).orElseThrow(()->new UsernameNotFoundException("User not found !"));
    }
}
