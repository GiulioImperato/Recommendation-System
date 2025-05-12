package com.example.recommendation.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Interaction> interactions;

	public User() {}

	public User(Long id, String username, List<Interaction> interactions) {
		this.id = id;
		this.username = username;
		this.interactions = interactions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Interaction> getInteractions() {
		return interactions;
	}

	public void setInteractions(List<Interaction> interactions) {
		this.interactions = interactions;
	}
}

