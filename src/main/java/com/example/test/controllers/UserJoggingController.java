package com.example.test.controllers;

import com.example.test.entities.Jogging;
import com.example.test.entities.User;
import com.example.test.services.UserJoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserJoggingController {

    @Autowired
    private UserJoggingService userJoggingService;

    @PutMapping("/{id}")
    public void createUserJogging(@PathVariable Long id, @RequestBody Jogging jogging){
        userJoggingService.createJogging(id, jogging);
    }

    @GetMapping
    public Iterable<User> getUsers(){
        return userJoggingService.getAll();
    }
}
