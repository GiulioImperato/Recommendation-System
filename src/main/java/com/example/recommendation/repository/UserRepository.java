package com.example.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recommendation.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

