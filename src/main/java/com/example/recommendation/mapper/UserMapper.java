package com.example.recommendation.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.recommendation.dto.UserDto;
import com.example.recommendation.entity.User;

@Component
public class UserMapper {

	private final InteractionMapper interactionMapper;

	public UserMapper(InteractionMapper interactionMapper) {
		this.interactionMapper = interactionMapper;
	}

	public UserDto toDto(User user) {

		return new UserDto.Builder()
				.id(user.getId())
				.username(user.getUsername())
				.interactions(user.getInteractions()
						.stream()
						.map(interactionMapper::toDTO)
						.collect(Collectors.toList()))
				.build();
	}
}
