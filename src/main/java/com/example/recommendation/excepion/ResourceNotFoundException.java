package com.example.recommendation.excepion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2485885013795949756L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
