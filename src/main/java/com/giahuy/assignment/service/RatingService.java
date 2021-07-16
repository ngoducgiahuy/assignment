package com.giahuy.assignment.service;

import java.util.List;

import com.giahuy.assignment.DTO.RatingDTO;
import com.giahuy.assignment.entity.Rating;
import com.giahuy.assignment.entity.Rating.RatingId;

public interface RatingService {
	public List<Rating> getAllRating();

	public Rating saveRating(Rating rating);

	public Rating updateRating(RatingId ratingId, Rating rating);

	public boolean deleteRatingById(RatingId ratingId);

	public RatingDTO convertToDTO(Rating rating);

	public Rating convertToEntity(RatingDTO ratingDTO);

	public Rating searchByProductIdAndCustomerId(long productId, long customerId);

	public List<Rating> searchByProductId(long productId);

	public List<Rating> searchByCustomerId(long customerId);
}