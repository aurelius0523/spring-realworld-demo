package com.aurelius.springrealworld.security;

import com.aurelius.springrealworld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RepoService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userEntity -> new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found", username)));
    }
}
