package com.example.recommendation.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class MovieRepositoryTest {

	@Autowired
	private MovieRepository movieRepository;

	@Test
	void shouldReturnRecommendedMoviesBasedOnGenresAndRatings() {
		// User5 has only implicit interactions on Movie5 and Movie6 of the Sci-Fi genre
		Long userId = 5L;
		Set<String> preferredGenres = Set.of("Sci-Fi");
		List<Object[]> results = movieRepository.findRecommendedMovies(userId, preferredGenres);

		assertTrue(!results.isEmpty());

		List<Long> recommendedMovieIds = results.stream()
				.map(row -> ((Number) row[0]).longValue())
				.toList();

		assertTrue(recommendedMovieIds.contains(1L)); // Contains Movie1
	}

	@Test
	void shouldExcludeAlreadyRatedMovies() {
		Long userId = 1L; // User1 rated Movie1 and Movie2 of the Sci-Fi genre
		Set<String> preferredGenres = Set.of("Sci-Fi");

		List<Object[]> results = movieRepository.findRecommendedMovies(userId, preferredGenres);

		List<Long> recommendedMovieIds = results.stream()
				.map(row -> ((Number) row[0]).longValue())
				.toList();

		assertTrue(!recommendedMovieIds.isEmpty()); // It's not empty because there are Movie5 and Movie6
		assertTrue(!recommendedMovieIds.contains(1L));
		assertTrue(!recommendedMovieIds.contains(2L));
	}

	@Test
	void shouldReturnEmptyIfNoPreferredGenresMatch() {
		Long userId = 5L; // User5 prefer the Sci-Fi genre
		Set<String> preferredGenres = Set.of("Musical", "Western");
		List<Object[]> results = movieRepository.findRecommendedMovies(userId, preferredGenres);
		assertTrue(results.isEmpty());
	}
}
