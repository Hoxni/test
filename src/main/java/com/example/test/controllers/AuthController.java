package com.example.test.controllers;

import com.example.test.security.AuthRequest;
import com.example.test.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody AuthRequest authRequest) throws Exception{
        System.out.println("Signup");
        String token = authService.addNewUser(authRequest.getUsername(), authRequest.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("username", authRequest.getUsername());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) throws Exception{
        String token = authService.login(authRequest.getUsername(), authRequest.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("username", authRequest.getUsername());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

}
