package com.example.test.services;

import com.example.test.entities.Jogging;
import com.example.test.entities.User;
import com.example.test.repositories.JoggingRepository;
import com.example.test.repositories.UserRepository;
import com.example.test.services.utils.WeekStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.*;

@Service
public class UserJoggingService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JoggingRepository joggingRepository;

    public void createJogging(Long userId, Jogging jogging) {

        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> {
            value.getJoggings().add(
                    joggingRepository.save(jogging)
            );
            userRepository.save(value);
        });
    }

    public void deleteJogging(Long userId, Long joggingId) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Jogging> jogging = joggingRepository.findById(joggingId);
        if (user.isPresent() && jogging.isPresent()){
            user.get().getJoggings().remove(jogging.get());
            userRepository.save(user.get());
            joggingRepository.deleteById(joggingId);
        }
    }

    public Iterable<Jogging> getUserJoggings(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            return user.get().getJoggings();
        }
        return null;
    }

    public void updateUserJogging(Long id, Jogging jogging){

            Optional<Jogging> buf = joggingRepository.findById(id);
            jogging.setId(id);
            buf.ifPresent(value -> {
                joggingRepository.save(jogging);
            });
    }

    public Iterable<WeekStatistics> getWeekStatistics(Long id){

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            List<Jogging> joggings = user.get().getJoggings();
            joggings.sort(Comparator.comparing(Jogging::getDateTime));
            List<WeekStatistics> weekStatistics = new ArrayList<>();

            int weekNum = joggings.get(0).getDateTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            List<Jogging> buff = new ArrayList<>();

            for(Jogging jogging: joggings){
                if (jogging.getDateTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == weekNum){
                    buff.add(jogging);
                } else {
                    weekStatistics.add(createWeekStatistics(buff));
                    buff.clear();
                    buff.add(jogging);
                    weekNum = jogging.getDateTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                }
            }
            weekStatistics.add(createWeekStatistics(buff));

            return weekStatistics;
        }
        return null;
    }

    private WeekStatistics createWeekStatistics(List<Jogging> list){
        int averageTime = 0;
        int averageSpeed;
        int totalDistance = 0;
        for (Jogging jogging: list){
            averageTime += jogging.getTime();
            totalDistance += jogging.getDistance();
        }
        averageSpeed = totalDistance / averageTime;
        averageTime /= list.size();

        LocalDate startDate = list.get(0).getDateTime().toLocalDate();
        LocalDate endDate = list.get(list.size() - 1).getDateTime().toLocalDate();

        return new WeekStatistics(startDate, endDate, averageSpeed, averageTime, totalDistance);
    }
}
