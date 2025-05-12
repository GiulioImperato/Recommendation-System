package com.example.recommendation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "interactions")
public class Interaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "movie_id")
	private Movie movie;

	private Integer rating;

	private Double viewPercentage;

	@Column(nullable = false)
	private boolean explicit;

	public Interaction() {}

	public Interaction(Long id, User user, Movie movie, Integer rating, Double viewPercentage, boolean explicit) {
		this.id = id;
		this.user = user;
		this.movie = movie;
		this.rating = rating;
		this.viewPercentage = viewPercentage;
		this.explicit = explicit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
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

	public boolean isExplicit() {
		return explicit;
	}

	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}
}
