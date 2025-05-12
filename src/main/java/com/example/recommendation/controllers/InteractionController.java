package com.example.recommendation.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.recommendation.dto.InteractionDto;
import com.example.recommendation.dto.InteractionRequestDto;
import com.example.recommendation.services.InteractionService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/interactions")
public class InteractionController {

	private final InteractionService interactionService;

	public InteractionController(InteractionService interactionService) {
		this.interactionService = interactionService;
	}

	@Operation(summary = "Retrieve a user's interaction history")
	@GetMapping("/{userId}/history")
	public ResponseEntity<List<InteractionDto>> getUserInteractionHistory(@PathVariable Long userId, @RequestParam Optional<String> type) {
		return ResponseEntity.ok(interactionService.getUserInteractionHistory(userId, type));
	}

	@Operation(summary = "Add an event (rating or view) for a movie by the user")
	@PutMapping("/addevent")
	public ResponseEntity<InteractionDto> addInteraction(@RequestBody InteractionRequestDto dto) {
		InteractionDto interactionDto = interactionService.addInteraction(dto);
		URI location = URI.create("/api/interactions/" + interactionDto.getId());
		if (interactionDto.isCreated()) {
			return ResponseEntity.created(location).body(interactionDto);
		} else {
			return ResponseEntity.ok(interactionDto);
		}
	}
}
