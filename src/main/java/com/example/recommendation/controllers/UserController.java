package com.example.recommendation.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recommendation.dto.UserDto;
import com.example.recommendation.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Retrieve all users in the system")
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(userService.findAllUsers());
	}
}
