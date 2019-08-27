package com.example.test;

import com.example.test.entities.Jogging;
import com.example.test.entities.User;
import com.example.test.repositories.UserRepository;
import com.example.test.services.UserJoggingService;
import com.example.test.statistics.WeekStatistics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {


	@Autowired
	private UserJoggingService userJoggingService;

	@MockBean
	private UserRepository userRepository;

	@Before
	public void setUp(){
		User user = new User("user", "user");
		List<Jogging> joggings = new ArrayList<>();
		for (int i = 7; i < 10; i++) {
			for (int j = 1; j < 4; j++) {
				joggings.add(new Jogging(j, j, LocalDateTime.of(2019, i, j, 1, 1)));
			}
		}
		user.setJoggings(joggings);

		Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));
	}

	@Test
	public void getWeekStatistics_4Weeks_ListSize4(){
		List<WeekStatistics> weekStatistics = (List<WeekStatistics>) userJoggingService.getWeekStatistics("user");
		Assert.assertEquals(4, weekStatistics.size());
	}

	@Test
	public void getWeekStatistics_AverageTimeAndDistance_WeekStatisticsTimeAndDistance() {
		List<WeekStatistics> weekStatistics = (List<WeekStatistics>) userJoggingService.getWeekStatistics("user");
		Assert.assertEquals(weekStatistics.get(0).getAverageTime(), 2, 0.0);
		Assert.assertEquals(weekStatistics.get(1).getAverageTime(), 2, 0.0);
		Assert.assertEquals(1, weekStatistics.get(2).getAverageTime(), 0.0);
		Assert.assertEquals(weekStatistics.get(3).getAverageTime(), 2.5, 0.0);
	}

}
