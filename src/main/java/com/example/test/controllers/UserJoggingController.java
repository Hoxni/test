package com.example.test.controllers;

import com.example.test.entities.Jogging;
import com.example.test.services.UserJoggingService;
import com.example.test.services.utils.WeekStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserJoggingController {

    @Autowired
    private UserJoggingService userJoggingService;

    @PostMapping("/{id}")
    public void createUserJogging(@PathVariable Long id, @RequestBody Jogging jogging){
        userJoggingService.createJogging(id, jogging);
    }

    @GetMapping("/{id}")
    public Iterable<Jogging> getUserJoggings(@PathVariable Long id){
        return userJoggingService.getUserJoggings(id);
    }

    @GetMapping("/{id}/statistics")
    public Iterable<WeekStatistics> getUserWeekStatistics(@PathVariable Long id){
        return userJoggingService.getWeekStatistics(id);
    }

    @PutMapping("/jogging/{id}")
    public void updateUserJogging(@PathVariable Long id, @RequestBody Jogging jogging){
        userJoggingService.updateUserJogging(id, jogging);
    }

    @DeleteMapping("{id}/jogging/{jogging}")
    public void deleteUserJogging(@PathVariable Long id, @PathVariable Long jogging){
        userJoggingService.deleteJogging(id, jogging);
    }

}
