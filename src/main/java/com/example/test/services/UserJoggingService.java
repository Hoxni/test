package com.example.test.services;

import com.example.test.entities.Jogging;
import com.example.test.entities.User;
import com.example.test.repositories.JoggingRepository;
import com.example.test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserJoggingService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JoggingRepository joggingRepository;

    public void createJogging(Long userId, Jogging jogging) {

        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> {
            value.getJoggingRecords().add(
                    joggingRepository.save(jogging)
            );
            userRepository.save(value);
        });
    }

    public void deleteJogging(Long userId, Long joggingId) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Jogging> jogging = joggingRepository.findById(joggingId);
        if (user.isPresent() && jogging.isPresent()){
            user.get().getJoggingRecords().remove(jogging.get());
            userRepository.save(user.get());
            joggingRepository.delete(jogging.get());
        }

    }

    public Iterable<User> getAll(){
        return userRepository.findAll();
    }
}
