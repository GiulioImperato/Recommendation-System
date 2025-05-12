package com.example.recommendation.mapper;

import org.springframework.stereotype.Component;

import com.example.recommendation.dto.InteractionDto;
import com.example.recommendation.entity.Interaction;

@Component
public class InteractionMapper {

	public InteractionDto toDTO(Interaction interaction) {

		return new InteractionDto.Builder()
				.id(interaction.getId())
				.movieId(interaction.getMovie().getId())
				.movieTitle(interaction.getMovie().getTitle())
				.rating(interaction.getRating())
				.viewPercentage(interaction.getViewPercentage())
				.explicit(interaction.isExplicit())
				.build();
	}
}
