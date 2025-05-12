package com.example.recommendation.dto;

public class InteractionDto {

	private Long id;
	private Long movieId;
	private String movieTitle;
	private Integer rating;
	private Double viewPercentage;
	private boolean explicit;
	private boolean created;

	// Private constructor callable only from Builder
	private InteractionDto(Builder builder) {
		this.id = builder.id;
		this.movieId = builder.movieId;
		this.movieTitle = builder.movieTitle;
		this.rating = builder.rating;
		this.viewPercentage = builder.viewPercentage;
		this.explicit = builder.explicit;
		this.created = builder.created;
	}

	// Getters

	public Long getId() {
		return id;
	}

	public Long getMovieId() {
		return movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public Integer getRating() {
		return rating;
	}

	public Double getViewPercentage() {
		return viewPercentage;
	}

	public boolean isExplicit() {
		return explicit;
	}

	public boolean isCreated() {
		return created;
	}

	// Builder
	public static class Builder {

		private Long id;
		private Long movieId;
		private String movieTitle;
		private Integer rating;
		private Double viewPercentage;
		private boolean explicit;
		private boolean created;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder movieId(Long movieId) {
			this.movieId = movieId;
			return this;
		}

		public Builder movieTitle(String movieTitle) {
			this.movieTitle = movieTitle;
			return this;
		}

		public Builder rating(Integer rating) {
			this.rating = rating;
			return this;
		}

		public Builder viewPercentage(Double viewPercentage) {
			this.viewPercentage = viewPercentage;
			return this;
		}

		public Builder explicit(boolean explicit) {
			this.explicit = explicit;
			return this;
		}

		public Builder created(boolean created) {
			this.created = created;
			return this;
		}

		public InteractionDto build() {
			return new InteractionDto(this);
		}
	}

	// Setters
	public void setCreated(boolean created) {
		this.created = created;
	}	
}
