package com.example.recommendation.strategy;

import java.util.List;

import com.example.recommendation.dto.MovieDto;

public interface MovieSorterStrategy {

	public List<MovieDto> sort(List<MovieDto> movies);
}
