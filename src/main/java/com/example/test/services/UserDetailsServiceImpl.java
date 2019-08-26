package com.example.test.services;

import com.example.test.details.UserDetailsImpl;
import com.example.test.entities.User;
import com.example.test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("UserDetailsService: " + s);
        User user = userRepository.findByUsername(s).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + s + " not found"));
        return new UserDetailsImpl(user);
    }
}
