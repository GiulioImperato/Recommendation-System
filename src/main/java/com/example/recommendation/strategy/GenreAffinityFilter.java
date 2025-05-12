package com.example.recommendation.strategy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.recommendation.dto.MovieDto;
import com.example.recommendation.entity.User;
import com.example.recommendation.excepion.ResourceNotFoundException;
import com.example.recommendation.repository.InteractionRepository;
import com.example.recommendation.repository.MovieRepository;
import com.example.recommendation.repository.UserRepository;

@Component
public class GenreAffinityFilter implements MovieSimilarityFilterStrategy {

	private final UserRepository userRepository;
	private final MovieRepository movieRepository;
	private final InteractionRepository interactionRepository;

	public GenreAffinityFilter(UserRepository userRepository, MovieRepository movieRepository, InteractionRepository interactionRepository) {
		this.userRepository = userRepository;
		this.movieRepository = movieRepository;
		this.interactionRepository = interactionRepository;
	}	

	@Override
	public List<MovieDto> filter(Long userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		Set<String> preferredGenres = interactionRepository.findByUser(user).stream()
				.filter(i -> i.getRating() != null && i.getRating() >= 4)
				.flatMap(i -> i.getMovie().getGenres().stream())
				.collect(Collectors.toSet());

		List<Object[]> results = movieRepository.findRecommendedMovies(userId, preferredGenres);

		return results.stream().map(row -> {
			return new MovieDto.Builder()
					.id(((Number) row[0]).longValue())
					.title((String) row[1])
					.interactionsCount(((Number) row[2]).longValue())
					.build();
		}).toList();
	}
}
