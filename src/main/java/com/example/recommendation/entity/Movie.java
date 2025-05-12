package com.example.recommendation.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

	@Id
	private Long id;

	@Column(nullable = false)
	private String title;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
	@Column(name = "genre")
	private Set<String> genres;

	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	private List<Interaction> interactions;

	public Movie() {}

	public Movie(Long id, String title, Set<String> genres, List<Interaction> interactions) {
		this.id = id;
		this.title = title;
		this.genres = genres;
		this.interactions = interactions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<String> getGenres() {
		return genres;
	}

	public void setGenres(Set<String> genres) {
		this.genres = genres;
	}

	public List<Interaction> getInteractions() {
		return interactions;
	}

	public void setInteractions(List<Interaction> interactions) {
		this.interactions = interactions;
	}
}
