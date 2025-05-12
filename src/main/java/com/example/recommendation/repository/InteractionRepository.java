package com.example.recommendation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recommendation.entity.Interaction;
import com.example.recommendation.entity.Movie;
import com.example.recommendation.entity.User;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

	public List<Interaction> findByUser(User user);

	public List<Interaction> findByMovie(Movie movie);

	public List<Interaction> findByUserAndMovie(User user, Movie movie);
}
