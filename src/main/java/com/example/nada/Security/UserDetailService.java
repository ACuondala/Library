package com.example.nada.Security;

import com.example.nada.Models.User;
import com.example.nada.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= this.userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User: "+email+" not founded"));
        return new UserDetail(user);
    }
}
