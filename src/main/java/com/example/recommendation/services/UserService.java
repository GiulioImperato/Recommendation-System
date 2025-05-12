package com.example.recommendation.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.recommendation.dto.UserDto;
import com.example.recommendation.entity.User;
import com.example.recommendation.mapper.UserMapper;
import com.example.recommendation.repository.UserRepository;

@Service
public class UserService {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public List<UserDto> findAllUsers() {

		LOGGER.info("Fetching all users");

		List<User> users = userRepository.findAll();
		List<UserDto> results = new ArrayList<>();

		for (User user: users) {
			UserDto dto = userMapper.toDto(user);
			results.add(dto);
		}

		return results;
	}
}

