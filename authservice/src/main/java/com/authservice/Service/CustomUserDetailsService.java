package com.authservice.Service;


import com.authservice.Entity.User;
import com.authservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //That Will load username from Database............................
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //find username from user table...........
        User user = userRepository.findByUsername(username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), Collections.emptyList());
    }
}
