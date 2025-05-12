package com.example.recommendation.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.recommendation.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query(value = """
			SELECT m.id, m.title, COUNT(*) AS interactions_count
			FROM movies m
			JOIN interactions i
			ON i.movie_id = m.id
			WHERE m.id NOT IN (
				SELECT movie_id
				FROM interactions
				WHERE user_id = :userId
				AND rating IS NOT NULL
			)
			AND (
				SELECT AVG(rating)
				FROM interactions
				WHERE movie_id = m.id
				AND rating IS NOT NULL
			) >= 4
			AND m.id in (
				select g.movie_id
				from movie_genres g
				where g.genre IN (:preferredGenres)
				GROUP by g.movie_id
			)
			GROUP BY m.id, m.title
			""", nativeQuery = true)
	public List<Object[]> findRecommendedMovies(
			@Param("userId") Long userId,
			@Param("preferredGenres") Set<String> preferredGenres);
}
