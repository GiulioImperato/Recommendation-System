package com.example.recommendation.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.recommendation.dto.MovieDto;
import com.example.recommendation.services.MovieService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@Operation(summary = "Retrieves a list of all available movies. You can filter the results using optional query parameters")
	@GetMapping
	public ResponseEntity<List<MovieDto>> getAllMovies(
			@RequestParam Optional<String> genre,
			@RequestParam Optional<Double> minRating,
			@RequestParam Optional<Double> maxRating) {
		return ResponseEntity.ok(movieService.getAllMovies(genre, minRating, maxRating));
	}
}
