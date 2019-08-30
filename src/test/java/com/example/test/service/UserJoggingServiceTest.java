package com.example.test.service;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserJoggingServiceTest {


	@Autowired
	private UserJoggingService userJoggingService;

	@MockBean
	private UserRepository userRepository;

	private static final int NUM_OF_WEEKS = 4;

	@Before
	public void setUp(){
		User user = new User("user", "user");

		/*
		Week 1		Week 2		Week 3		Week 4
		01.07.2019	01.08.2019	01.09.2019	02.09.2019
		02.07.2019	02.08.2019				03.09.2019
		03.07.2019	03.08.2019
		 */
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
	public void shouldCountNumberOfWeeksCorrectly(){
		List<WeekStatistics> weekStatistics =
				(List<WeekStatistics>) userJoggingService.getWeekStatistics("user");

		Assert.assertEquals(NUM_OF_WEEKS, weekStatistics.size());
	}

	@Test
	public void shouldCalculateAverageTimeForWeekStatisticsCorrectly() {
		List<WeekStatistics> weekStatistics =
				(List<WeekStatistics>) userJoggingService.getWeekStatistics("user");

		weekStatistics.sort(Comparator.comparing(WeekStatistics::getStartDate));

		Assert.assertEquals(2, weekStatistics.get(0).getAverageTime(), 0.0);
		Assert.assertEquals(2, weekStatistics.get(1).getAverageTime(), 0.0);
		Assert.assertEquals(1, weekStatistics.get(2).getAverageTime(), 0.0);
		Assert.assertEquals(2.5, weekStatistics.get(3).getAverageTime(), 0.0);
	}

	@Test
	public void shouldCalculateAverageDistanceForWeekStatisticsCorrectly() {
		List<WeekStatistics> weekStatistics =
				(List<WeekStatistics>) userJoggingService.getWeekStatistics("user");

		weekStatistics.sort(Comparator.comparing(WeekStatistics::getStartDate));

		Assert.assertEquals(2, weekStatistics.get(0).getAverageTime(), 0.0);
		Assert.assertEquals(2, weekStatistics.get(1).getAverageTime(), 0.0);
		Assert.assertEquals(1, weekStatistics.get(2).getAverageTime(), 0.0);
		Assert.assertEquals(2.5, weekStatistics.get(3).getAverageTime(), 0.0);
	}

	@Test
	public void shouldSetStartDateOfWeekCorrectly(){
		List<WeekStatistics> weekStatistics =
				(List<WeekStatistics>) userJoggingService.getWeekStatistics("user");

		weekStatistics.sort(Comparator.comparing(WeekStatistics::getStartDate));

		Assert.assertEquals(LocalDate.of(2019, 7, 1), weekStatistics.get(0).getStartDate());
		Assert.assertEquals(LocalDate.of(2019, 8, 1), weekStatistics.get(1).getStartDate());
		Assert.assertEquals(LocalDate.of(2019, 9, 1), weekStatistics.get(2).getStartDate());
		Assert.assertEquals(LocalDate.of(2019, 9, 2), weekStatistics.get(3).getStartDate());
	}

	@Test
	public void shouldSetEndDateOfWeekCorrectly(){
		List<WeekStatistics> weekStatistics =
				(List<WeekStatistics>) userJoggingService.getWeekStatistics("user");

		weekStatistics.sort(Comparator.comparing(WeekStatistics::getStartDate));

		Assert.assertEquals(LocalDate.of(2019, 7, 3), weekStatistics.get(0).getEndDate());
		Assert.assertEquals(LocalDate.of(2019, 8, 3), weekStatistics.get(1).getEndDate());
		Assert.assertEquals(LocalDate.of(2019, 9, 1), weekStatistics.get(2).getEndDate());
		Assert.assertEquals(LocalDate.of(2019, 9, 3), weekStatistics.get(3).getEndDate());
	}

}
