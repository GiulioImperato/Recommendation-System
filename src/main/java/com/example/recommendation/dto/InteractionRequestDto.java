package com.example.recommendation.dto;

public class InteractionRequestDto {

	private Long userId;
	private Long movieId;
	private Integer rating;
	private Double viewPercentage;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Double getViewPercentage() {
		return viewPercentage;
	}

	public void setViewPercentage(Double viewPercentage) {
		this.viewPercentage = viewPercentage;
	}
}
