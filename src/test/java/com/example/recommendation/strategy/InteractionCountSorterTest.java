package com.example.recommendation.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.recommendation.dto.MovieDto;

class InteractionCountSorterTest {

	private InteractionCountSorter sorter;

	@BeforeEach
	void setUp() {
		sorter = new InteractionCountSorter();
	}

	@Test
	void shouldSortMoviesByDescendingInteractionCount() {

		MovieDto m1 = new MovieDto.Builder().id(1L).title("Movie1").interactionsCount(1032L).build();
		MovieDto m2 = new MovieDto.Builder().id(2L).title("Movie2").interactionsCount(67L).build();
		MovieDto m3 = new MovieDto.Builder().id(2L).title("Movie3").interactionsCount(966L).build();
		MovieDto m4 = new MovieDto.Builder().id(3L).title("Movie4").interactionsCount(801L).build();
		MovieDto m5 = new MovieDto.Builder().id(3L).title("Movie5").interactionsCount(923L).build();
		MovieDto m6 = new MovieDto.Builder().id(3L).title("Movie6").interactionsCount(965L).build();
		MovieDto m7 = new MovieDto.Builder().id(3L).title("Movie7").interactionsCount(965L).build();

		List<MovieDto> movies = List.of(m1, m2, m3, m4, m5, m6, m7);
		List<MovieDto> sorted = sorter.sort(movies);

		/*
		 * Movie1 – 1032
		 * Movie3 – 966
		 * Movie6 – 965
		 * Movie7 – 965
		 * Movie5 – 923
		 * Movie4 – 801
		 * Movie2 – 67
		 */

		assertTrue(sorted.getFirst().getTitle().equals("Movie1"));
		assertTrue(sorted.getLast().getTitle().equals("Movie2"));
	}
}
