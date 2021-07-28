package com.giahuy.assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.giahuy.assignment.entity.Rating;
import com.giahuy.assignment.entity.Rating.RatingId;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId>{
	
	Optional<List<Rating>> findByRatingIdCustomerId(long customerId);
	
	Optional<List<Rating>> findByRatingIdProductId(long productId);
	
	@Query(value = "Select AVG(e.rating_point) FROM Rating e WHERE e.product_id = :productId", nativeQuery = true) 
	Optional<Float> findAvgRating(@Param("productId") Long productId);
}