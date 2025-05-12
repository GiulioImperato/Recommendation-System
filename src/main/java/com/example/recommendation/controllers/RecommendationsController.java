package com.example.recommendation.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recommendation.dto.MovieDto;
import com.example.recommendation.services.RecommendationsService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationsController {

	private final RecommendationsService recommendationsService;

	public RecommendationsController(RecommendationsService recommendationsService) {
		this.recommendationsService = recommendationsService;
	}

	@Operation(summary = "Get movie recommendations for a user")
	@GetMapping("/{userId}")
	public ResponseEntity<List<MovieDto>> getRecommendations(@PathVariable Long userId) {
		return ResponseEntity.ok(recommendationsService.getRecommendedMovies(userId));
	}
}
