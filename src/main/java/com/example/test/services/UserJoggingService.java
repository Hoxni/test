package com.example.test.services;

import com.example.test.entities.Jogging;
import com.example.test.entities.User;
import com.example.test.repositories.JoggingRepository;
import com.example.test.repositories.UserRepository;
import com.example.test.statistics.WeekStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public void createJogging(String username, Jogging jogging) {

        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(value -> {
            value.getJoggings().add(
                    joggingRepository.save(jogging)
            );
            userRepository.save(value);
        });
    }

    public void deleteJogging(String username, Long joggingId) {

        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
        Optional<Jogging> jogging = joggingRepository.findById(joggingId);
        boolean hasJogging = false;
        for (Jogging j: user.getJoggings()){
            if (j.getId().equals(joggingId)){
                hasJogging = true;
                break;
            }
        }
        if (!hasJogging) throw new NoSuchElementException("User has no jogging with id: " + joggingId);
        if (jogging.isPresent()){
            user.getJoggings().remove(jogging.get());
            userRepository.save(user);
            joggingRepository.deleteById(joggingId);
        }
    }

    public Iterable<Jogging> getUserJoggings(String username) throws UsernameNotFoundException{
        //System.out.println("Get user jogs: " + username);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
        return user.getJoggings();
    }

    public void updateUserJogging(String username, Long id, Jogging jogging) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
        boolean hasJogging = false;
        for (Jogging j: user.getJoggings()){
            if (j.getId().equals(id)){
                hasJogging = true;
                break;
            }
        }
        if (!hasJogging) throw new NoSuchElementException("User has no jogging with id: " + id);
        Optional<Jogging> buf = joggingRepository.findById(id);
        jogging.setId(id);
        buf.ifPresent(value -> joggingRepository.save(jogging));
    }

    public Iterable<WeekStatistics> getWeekStatistics(String username){

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            List<Jogging> joggings = user.get().getJoggings();

            Map<Integer, List<Jogging>> weeks = new HashMap<>();
            List<WeekStatistics> weekStatistics = new ArrayList<>();

            for(Jogging jogging: joggings){
                int weekNum = jogging.getDateTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                if (weeks.containsKey(weekNum)){
                    weeks.get(weekNum).add(jogging);
                } else {
                    List<Jogging> list = new ArrayList<>();
                    list.add(jogging);
                    weeks.put(weekNum, list);
                }
            }

            for (List<Jogging> list: weeks.values()) {
                weekStatistics.add(createWeekStatistics(list));
            }

            return weekStatistics;
        }
        return null;
    }

    private WeekStatistics createWeekStatistics(List<Jogging> list){
        double averageTime = 0;
        double averageSpeed;
        int totalDistance = 0;
        for (Jogging jogging: list){
            averageTime += jogging.getTime();
            totalDistance += jogging.getDistance();
        }

        averageSpeed = totalDistance / averageTime;
        averageTime /= list.size();

        LocalDate startDate = Collections.min(list, Comparator.comparing(Jogging::getDateTime))
                .getDateTime().toLocalDate();
        LocalDate endDate = Collections.max(list, Comparator.comparing(Jogging::getDateTime))
                .getDateTime().toLocalDate();

        return new WeekStatistics(startDate, endDate, averageSpeed, averageTime, totalDistance);
    }
}
