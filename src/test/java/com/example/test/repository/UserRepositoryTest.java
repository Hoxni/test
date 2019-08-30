package com.example.test.repository;

import com.example.test.entities.User;
import com.example.test.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User expected;

    private static final String USERNAME = "expected";
    private static final int REPO_SIZE = 2;

    @Before
    public void setUp(){
        expected = new User();
        expected.setId(1L);
        expected.setUsername(USERNAME);
        expected.setPassword("pass");
        expected.setJoggings(new ArrayList<>());

        userRepository.save(expected);
    }

    @Test
    public void shouldFindByUsernameCorrectly(){
        Optional<User> actual = userRepository.findByUsername(USERNAME);
        Assert.assertEquals(expected, actual.get());
    }

    @Test
    public void shouldGenerateId(){
        User user = new User("user1", "pass1");
        User savedUser = userRepository.save(user);
        Assert.assertNotNull(savedUser.getId());
    }

    @Test
    public void shouldFindAllUsers(){
        List<User> actualUsers = (List<User>) userRepository.findAll();
        Assert.assertEquals(REPO_SIZE, actualUsers.size());
    }
}
