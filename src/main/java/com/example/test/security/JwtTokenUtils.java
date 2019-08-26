package com.example.test.security;

import com.example.test.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtils {

    public String getUsernameFromToken(String token){
        return token;
    }

    public String generateToken(UserDetails userDetails){
        return userDetails.getUsername();
    }

    public String generateToken(User user){
        return user.getUsername();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        return getUsernameFromToken(token).equals(userDetails.getUsername());
    }
}
