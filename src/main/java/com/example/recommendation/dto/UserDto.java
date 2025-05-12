package com.example.recommendation.dto;

import java.util.List;

public class UserDto {

	private Long id;
	private String username;
	private List<InteractionDto> interactions;

	// Private constructor callable only from Builder
	private UserDto(Builder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.interactions = builder.interactions;
	}

	// Getters

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public List<InteractionDto> getInteractions() {
		return interactions;
	}

	// Builder
	public static class Builder {

		private Long id;
		private String username;
		private List<InteractionDto> interactions;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder interactions(List<InteractionDto> interactions) {
			this.interactions = interactions;
			return this;
		}

		public UserDto build() {
			return new UserDto(this);
		}
	}
}
