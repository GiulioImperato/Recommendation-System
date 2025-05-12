package com.example.recommendation.dto;

import java.util.Set;

public class MovieDto {

	private Long id;
	private String title;
	private Set<String> genres;
	private Long interactionsCount;
	private Double averageRating;

	// Private constructor callable only from Builder
	private MovieDto(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.genres = builder.genres;
		this.interactionsCount = builder.interactionsCount;
	}

	// Getters

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Set<String> getGenres() {
		return genres;
	}

	public Long getInteractionsCount() {
		return interactionsCount;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	// Builder
	public static class Builder {

		private Long id;
		private String title;
		private Set<String> genres;
		private Long interactionsCount;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder genres(Set<String> genres) {
			this.genres = genres;
			return this;
		}

		public Builder interactionsCount(Long interactionsCount) {
			this.interactionsCount = interactionsCount;
			return this;
		}

		public MovieDto build() {
			return new MovieDto(this);
		}
	}

	// Setters

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}
}
