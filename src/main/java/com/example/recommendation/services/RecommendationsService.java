package com.example.recommendation.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.recommendation.dto.MovieDto;
import com.example.recommendation.strategy.GenreAffinityFilter;
import com.example.recommendation.strategy.InteractionCountSorter;

@Service
public class RecommendationsService {

	private final static Logger LOGGER = LoggerFactory.getLogger(RecommendationsService.class);

	private final GenreAffinityFilter genreAffinityFilter;
	private final InteractionCountSorter interactionCountSorter;

	public RecommendationsService(GenreAffinityFilter genreAffinityFilter, InteractionCountSorter interactionCountSorter) {
		this.genreAffinityFilter = genreAffinityFilter;
		this.interactionCountSorter = interactionCountSorter;
	}

	public List<MovieDto> getRecommendedMovies(Long userId) {
		LOGGER.info("Generating recommendations for user {} using strategies: {}", userId, genreAffinityFilter.getClass().getSimpleName() + ", " + interactionCountSorter.getClass().getSimpleName());
		List<MovieDto> filtered = genreAffinityFilter.filter(userId);
		LOGGER.debug("Number of recommendations: {}", filtered.size());
		return interactionCountSorter.sort(filtered);
	}
}
