package com.giahuy.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giahuy.assignment.entity.Rating;
import com.giahuy.assignment.entity.Rating.RatingId;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId>{
	
	List<Rating> findByRatingIdCustomerId(long customerId);
	
	List<Rating> findByRatingIdProductId(long productId);
	
//	@Query(value = "Select * from rating r where r.customer_id = :customerId", nativeQuery = true)
//	List<Rating> findByCustomerId(@Param("customerId") Long customerId);
//	
//	@Query(value = "Select * from rating r where r.product_id = :productId", nativeQuery = true)
//	List<Rating> findByProductId(@Param("productId") Long productId);
//	
//	@Query(value = "Select * from rating r where r.product_id = :productId and r.customer_id = :customerId", nativeQuery = true)
//	Rating findByProductIdAndCustomerId(@Param("productId") Long productId, @Param("customerId") Long customerId);
	
}