package com.example.recommendation.bootstrap;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.recommendation.entity.Interaction;
import com.example.recommendation.entity.Movie;
import com.example.recommendation.entity.User;
import com.example.recommendation.repository.InteractionRepository;
import com.example.recommendation.repository.MovieRepository;
import com.example.recommendation.repository.UserRepository;
import com.opencsv.CSVReader;

import jakarta.annotation.PostConstruct;

@Component
@Profile("!test")
public class DataLoader {

	private final UserRepository userRepository;
	private final MovieRepository movieRepository;
	private final InteractionRepository interactionRepository;

	public DataLoader(UserRepository userRepository,
			MovieRepository movieRepository,
			InteractionRepository interactionRepository) {
		this.userRepository = userRepository;
		this.movieRepository = movieRepository;
		this.interactionRepository = interactionRepository;
	}

	@PostConstruct
	public void loadData() throws Exception {
		loadUsers();
		loadMovies();
		loadInteractions();
	}

	private void loadUsers() throws Exception {
		try (CSVReader reader = new CSVReader(new InputStreamReader(
				new ClassPathResource("data/csv/users.csv").getInputStream()))) {
			reader.readNext(); // Skip header

			String[] line;
			while ((line = reader.readNext()) != null) {
				Long id = Long.parseLong(line[0]);
				String username = line[1];

				if (!userRepository.existsById(id)) {
					User user = new User();
					user.setId(id);
					user.setUsername(username);
					userRepository.save(user);
				}
			}
		}
	}

	private void loadMovies() throws Exception {
		try (CSVReader reader = new CSVReader(new InputStreamReader(
				new ClassPathResource("data/csv/movies.csv").getInputStream()))) {
			reader.readNext(); // Skip header

			String[] line;
			while ((line = reader.readNext()) != null) {
				Long id = Long.parseLong(line[0]);
				String title = line[1];
				Set<String> genres = new HashSet<>(Arrays.asList(line[2].split("\\|")));

				if (!movieRepository.existsById(id)) {
					Movie movie = new Movie();
					movie.setId(id);
					movie.setTitle(title);
					movie.setGenres(genres);
					movieRepository.save(movie);
				}
			}
		}
	}

	private void loadInteractions() throws Exception {
		try (CSVReader reader = new CSVReader(new InputStreamReader(
				new ClassPathResource("data/csv/ratings.csv").getInputStream()))) {
			reader.readNext(); // Skip header

			String[] line;
			while ((line = reader.readNext()) != null) {
				Long userId = Long.parseLong(line[0]);
				Long movieId = Long.parseLong(line[1]);
				String ratingStr = line[2];
				String viewStr = line[3];

				Optional<User> userOpt = userRepository.findById(userId);
				Optional<Movie> movieOpt = movieRepository.findById(movieId);

				if (userOpt.isEmpty() || movieOpt.isEmpty()) continue;

				User user = userOpt.get();
				Movie movie = movieOpt.get();

				boolean isExplicit = ratingStr != null && !ratingStr.isEmpty();
				boolean alreadyRatedExplicitly = interactionRepository
						.findByUserAndMovie(user, movie)
						.stream()
						.anyMatch(Interaction::isExplicit);

				if (isExplicit && alreadyRatedExplicitly) continue;

				Interaction interaction = new Interaction();
				interaction.setUser(user);
				interaction.setMovie(movie);

				if (isExplicit) {
					interaction.setRating(Integer.parseInt(ratingStr));
					interaction.setExplicit(true);
					interaction.setViewPercentage(viewStr != null && !viewStr.isEmpty()
							? Double.parseDouble(viewStr) : null);
					interactionRepository.save(interaction);
				} else if (viewStr != null && !viewStr.isEmpty()) {
					double view = Double.parseDouble(viewStr);
					int inferredRating = view >= 80 ? 5 : view >= 60 ? 4 : 0;

					if (inferredRating > 0) {
						interaction.setRating(inferredRating);
						interaction.setExplicit(false);
						interaction.setViewPercentage(view);
						interactionRepository.save(interaction);
					}
				}
			}
		}
	}
}
