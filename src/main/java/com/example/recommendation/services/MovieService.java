package com.example.recommendation.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.recommendation.dto.MovieDto;
import com.example.recommendation.entity.Interaction;
import com.example.recommendation.entity.Movie;
import com.example.recommendation.mapper.MovieMapper;
import com.example.recommendation.repository.InteractionRepository;
import com.example.recommendation.repository.MovieRepository;

@Service
public class MovieService {

	private final static Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

	private final MovieRepository movieRepository;
	private final InteractionRepository interactionRepository;
	private final MovieMapper movieMapper;

	public MovieService(MovieRepository movieRepository, InteractionRepository interactionRepository, MovieMapper movieMapper) {
		this.movieRepository = movieRepository;
		this.interactionRepository = interactionRepository;
		this.movieMapper = movieMapper;
	}

	public List<MovieDto> getAllMovies(Optional<String> genre, Optional<Double> minRating, Optional<Double> maxRating) {

		LOGGER.info("Fetching all movies");

		List<Movie> movies = movieRepository.findAll();
		List<MovieDto> results = new ArrayList<>();

		for (Movie movie: movies) {
			MovieDto dto = movieMapper.toDto(movie);

			List<Interaction> interactions = interactionRepository.findByMovie(movie);
			List<Integer> ratings = interactions.stream().
					map(Interaction::getRating)
					.filter(Objects::nonNull)
					.toList();

			double avg = ratings.isEmpty() ? 0.0 : ratings.stream().mapToInt(i -> i).average().orElse(0.0);
			dto.setAverageRating(avg);	

			results.add(dto);
		}

		LOGGER.debug("Returning {} movies", results.size());

		return results.stream()
				.filter(m -> genre.map(g -> m.getGenres().contains(g)).orElse(true))
				.filter(m -> minRating.map(min -> m.getAverageRating() >= min).orElse(true))
				.filter(m -> maxRating.map(max -> m.getAverageRating() <= max).orElse(true))
				.collect(Collectors.toList());
	}
}
