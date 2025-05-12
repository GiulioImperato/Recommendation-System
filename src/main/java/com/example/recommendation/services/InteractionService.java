package com.example.recommendation.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.recommendation.dto.InteractionDto;
import com.example.recommendation.dto.InteractionRequestDto;
import com.example.recommendation.entity.Interaction;
import com.example.recommendation.entity.Movie;
import com.example.recommendation.entity.User;
import com.example.recommendation.excepion.ResourceNotFoundException;
import com.example.recommendation.mapper.InteractionMapper;
import com.example.recommendation.repository.InteractionRepository;
import com.example.recommendation.repository.MovieRepository;
import com.example.recommendation.repository.UserRepository;
import com.example.recommendation.utils.RatingUtils;

@Service
public class InteractionService {

	private final static Logger LOGGER = LoggerFactory.getLogger(InteractionService.class);

	private final InteractionRepository interactionRepostiory;
	private final UserRepository userRepository;
	private final MovieRepository movieRepository;
	private final InteractionMapper interactionMapper;

	public InteractionService(InteractionRepository interactionRepostiory, UserRepository userRepository, 
			MovieRepository movieRepository, InteractionMapper interactionMapper) {
		this.interactionRepostiory = interactionRepostiory;
		this.userRepository = userRepository;
		this.movieRepository = movieRepository;
		this.interactionMapper = interactionMapper;
	}

	public List<InteractionDto> getUserInteractionHistory(Long userId, Optional<String> typeFilter) {

		LOGGER.info("Get user interaction history: userId={}", userId);

		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		List<Interaction> interactions = interactionRepostiory.findByUser(user);

		return interactions.stream()
				.filter(i -> typeFilter.map(type -> {
					if (type.equalsIgnoreCase("rating")) return	i.isExplicit();
					if (type.equalsIgnoreCase("view")) return !i.isExplicit();
					return true;
				}).orElse(true))
				.map(interactionMapper::toDTO)
				.collect(Collectors.toList());
	}

	public InteractionDto addInteraction(InteractionRequestDto dto) {

		Integer rating = dto.getRating();
		Double viewPercentage = dto.getViewPercentage();

		if (rating == null && viewPercentage == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating or valid viewPercentage must be provided.");
		}

		User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
		Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + dto.getMovieId()));

		List<Interaction> interactions = interactionRepostiory.findByUserAndMovie(user, movie);
		Interaction interaction = null;
		if (!interactions.isEmpty()) {
			interaction = interactions.getFirst();
		}

		boolean isExplicit = rating != null;
		boolean isCreated = false;

		if (interaction != null) { // UPDATE
			if (!interaction.isExplicit()) {
				interaction.setExplicit(isExplicit);
			}
			if (rating != null) {
				interaction.setRating(rating);
			}

			// Only update the view percentage if the new value is higher than the previous one,
			// indicating that the user has watched more of the movie
			if (viewPercentage != null && interaction.getViewPercentage() != null && interaction.getViewPercentage() < viewPercentage) {
				interaction.setViewPercentage(viewPercentage);
			}
		} else { // INSERT

			if (!isExplicit) {
				rating = RatingUtils.calculateImplicitRating(viewPercentage);
			}

			interaction = new Interaction();
			interaction.setUser(user);
			interaction.setMovie(movie);
			interaction.setRating(rating);
			interaction.setViewPercentage(viewPercentage);
			interaction.setExplicit(isExplicit);
			isCreated = true;
		}

		interaction = interactionRepostiory.save(interaction);

		InteractionDto interactionDto = interactionMapper.toDTO(interaction);
		interactionDto.setCreated(isCreated);
		return interactionDto;
	}
}
