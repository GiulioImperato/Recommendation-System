package com.example.recommendation.mapper;

import org.springframework.stereotype.Component;

import com.example.recommendation.dto.MovieDto;
import com.example.recommendation.entity.Movie;

@Component
public class MovieMapper {

	public MovieDto toDto(Movie movie) {

		return new MovieDto.Builder()
				.id(movie.getId())
				.title(movie.getTitle())
				.genres(movie.getGenres())
				.build();
	}
}
