package com.example.test.services;

import com.example.test.entities.User;
import com.example.test.repositories.UserRepository;
import com.example.test.security.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserRepository userRepository;

    public String addNewUser(String username, String password) throws Exception{
        if (!userRepository.findByUsername(username).isPresent()) {
            userRepository.save(new User(username, password));
            userRepository.findAll().forEach(v->System.out.println("Id: " + v.getId() + " name: " + v.getUsername()));
            return login(username, password);
        } else throw new BadCredentialsException("Existing username");
    }

    public String login(String username, String password) throws Exception{
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user"));
        if (user.getPassword().equals(password)){
            try {
                auth(username, password);
            }catch (Exception e){
                e.printStackTrace();
            }
            return jwtTokenUtils.generateToken(user);
        } else throw new BadCredentialsException("Wrong username/pass");
    }

    private void auth(String username, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Auth exception", e);
        }
    }

    public void logout(User user){

    }
}
