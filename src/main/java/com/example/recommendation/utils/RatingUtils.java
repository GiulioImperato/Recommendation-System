package com.example.recommendation.utils;

public final class RatingUtils {

	private RatingUtils() { }

	public static Integer calculateImplicitRating(Double viewPercentage) {
		if (viewPercentage == null) return null;
		if (viewPercentage > 80) return 5;
		if (viewPercentage > 60) return 4;
		if (viewPercentage > 40) return 3;
		if (viewPercentage > 20) return 2;
		return 1;
	}
}
