package com.example.recommendation.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class RatingUtilsTest {

	@Test
	public void testCalculateImplicitRating() {

		assertNull(RatingUtils.calculateImplicitRating(null), "The rating should be null if the input is null.");
		assertEquals(1, RatingUtils.calculateImplicitRating(0.0));
		assertEquals(1, RatingUtils.calculateImplicitRating(10.0));
		assertEquals(1, RatingUtils.calculateImplicitRating(20.0));
		assertEquals(2, RatingUtils.calculateImplicitRating(21.0));
		assertEquals(2, RatingUtils.calculateImplicitRating(25.0));
		assertEquals(3, RatingUtils.calculateImplicitRating(45.0));
		assertEquals(4, RatingUtils.calculateImplicitRating(65.0));
		assertEquals(4, RatingUtils.calculateImplicitRating(80.0));
		assertEquals(5, RatingUtils.calculateImplicitRating(95.0));
	}
}
