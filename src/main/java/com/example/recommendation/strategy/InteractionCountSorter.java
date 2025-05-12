package com.example.recommendation.strategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.recommendation.dto.MovieDto;

@Component
public class InteractionCountSorter implements MovieSorterStrategy {

	@Override
	public List<MovieDto> sort(List<MovieDto> movies) {
		return movies.stream()
				.sorted(Comparator.comparingLong(m -> {
					return ((MovieDto) m).getInteractionsCount();
				}).reversed())
				.collect(Collectors.toList());
	}
}
