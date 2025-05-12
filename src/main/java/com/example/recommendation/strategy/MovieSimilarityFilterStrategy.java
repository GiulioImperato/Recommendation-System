package com.example.recommendation.strategy;

import java.util.List;

import com.example.recommendation.dto.MovieDto;

public interface MovieSimilarityFilterStrategy {

	public List<MovieDto> filter(Long userId);
}
