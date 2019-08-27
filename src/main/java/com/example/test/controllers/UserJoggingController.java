package com.example.test.controllers;

import com.example.test.entities.Jogging;
import com.example.test.services.UserJoggingService;
import com.example.test.statistics.WeekStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserJoggingController {

    @Autowired
    private UserJoggingService userJoggingService;

    @PostMapping
    public void createUserJogging(Authentication authentication, @RequestBody Jogging jogging){
        System.out.println("Auth name: " + authentication.getName());
        userJoggingService.createJogging(authentication.getName(), jogging);
    }

    @GetMapping
    public Iterable<Jogging> getUserJoggings(Authentication authentication){
        System.out.println("Auth name: " + authentication.getName());
        return userJoggingService.getUserJoggings(authentication.getName());
    }

    @GetMapping("/statistics")
    public Iterable<WeekStatistics> getUserWeekStatistics(Authentication authentication){
        return userJoggingService.getWeekStatistics(authentication.getName());
    }

    @PutMapping("/jogging/{id}")
    public void updateUserJogging(Authentication authentication, @PathVariable Long id, @RequestBody Jogging jogging){
        userJoggingService.updateUserJogging(authentication.getName(), id, jogging);
    }

    @DeleteMapping("/jogging/{joggingId}")
    public void deleteUserJogging(Authentication authentication, @PathVariable Long joggingId){
        userJoggingService.deleteJogging(authentication.getName(), joggingId);
    }

}
