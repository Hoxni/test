package com.example.test.repository;

import com.example.test.entities.Jogging;
import com.example.test.repositories.JoggingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JoggingRepositoryTest {

    @Autowired
    private JoggingRepository joggingRepository;

    private Jogging expected;

    private static final Long ID = 1L;

    @Before
    public void setUp(){
        expected = new Jogging();
        expected.setId(ID);
        expected.setDistance(1);
        expected.setTime(1);
        expected.setDateTime(LocalDateTime.of(2020, 5, 5, 5, 5));

        joggingRepository.save(expected);
    }

    @Test
    public void shouldFindByIdCorrectly(){
        Optional<Jogging> actual = joggingRepository.findById(ID);
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void shouldGenerateId(){
        Jogging jogging = new Jogging(1, 1, LocalDateTime.now());
        Jogging savedJogging = joggingRepository.save(jogging);
        Assert.assertNotNull(savedJogging.getId());
    }

    @Test
    public void shouldBeUpdated(){
        Jogging updated = expected;
        expected.setDistance(2);
        expected.setTime(2);
        joggingRepository.save(updated);
        Optional<Jogging> actual = joggingRepository.findById(ID);
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void shouldDeleteById(){
        Jogging jogging = new Jogging();
        jogging.setId(3L);
        joggingRepository.save(jogging);

        joggingRepository.deleteById(3L);
        Optional<Jogging> deleted = joggingRepository.findById(3L);
        Assert.assertFalse(deleted.isPresent());
    }
}
