package com.example.recommendation.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.example.recommendation.dto.MovieDto;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class GenreAffinityFilterTest {

	@Autowired
	private GenreAffinityFilter genreAffinityFilter;

	@Test
	void shouldRecommendMoviesBasedOnHighRatedGenres() {
		// User1 gave 5 to Movie1 (Sci-Fi) → expects a suggestion based on that genre
		Long userId = 1L;
		List<MovieDto> recommended = genreAffinityFilter.filter(userId);
		assertTrue(recommended.size() == 2); // Contains Movie5 and Movie6
	}

	@Test
	void shouldReturnEmptyIfUserHasNoRatedMovies() {
		// User 6 has no interaction, so there are no preferred genders
		Long userId = 6L;
		List<MovieDto> recommended = genreAffinityFilter.filter(userId);
		assertTrue(recommended.isEmpty());
	}
}
