package com.giahuy.assignment.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giahuy.assignment.DTO.RatingDTO;
import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.entity.Rating;
import com.giahuy.assignment.entity.Rating.RatingId;
import com.giahuy.assignment.entity.User;
import com.giahuy.assignment.repository.RatingRepository;
import com.giahuy.assignment.service.ProductService;
import com.giahuy.assignment.service.RatingService;
import com.giahuy.assignment.service.UserService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	RatingRepository ratingRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserService userService;

	@Autowired
	ProductService productService;

	@Override
	public List<Rating> getAllRating() {
		return ratingRepo.findAll();
	}

	@Override
	public Rating saveRating(Rating rating) {
		if(ratingRepo.existsById(rating.getRatingId())) return null;
		Rating newRatingCheck = this.checkAndSetUserAndProductForEntity(rating);
		if (newRatingCheck != null) {
			newRatingCheck.setRatingDate(LocalDateTime.now());
			return ratingRepo.save(newRatingCheck);
		}
		return null;
	}

	@Override
	public Rating updateRating(RatingId ratingId, Rating rating) {
		return ratingRepo.findById(ratingId).map(ratingUpdate->{
						ratingUpdate.setComment(rating.getComment());
						ratingUpdate.setRatingDate(LocalDateTime.now());
						ratingUpdate.setRatingPoint(rating.getRatingPoint());
						return ratingRepo.save(ratingUpdate);
		}).orElse(null);
	}

	@Override
	public boolean deleteRatingById(RatingId ratingId) {
		return ratingRepo.findById(ratingId).map(rating -> {
			System.out.println(ratingId);
			ratingRepo.deleteById(ratingId);
			return true;
		}).orElse(false);

	}

	@Override
	public RatingDTO convertToDTO(Rating rating) {
		RatingDTO ratingDTO = modelMapper.map(rating, RatingDTO.class);
		ratingDTO.setCustomerId(rating.getRatingId().getCustomerId());
		ratingDTO.setProductId(rating.getRatingId().getProductId());
		return ratingDTO;
	}

	@Override
	public Rating convertToEntity(RatingDTO ratingDTO) {
		Rating rating = modelMapper.map(ratingDTO, Rating.class);
		RatingId ratingId = new RatingId(ratingDTO.getCustomerId(), ratingDTO.getProductId());
		rating.setRatingId(ratingId);
		return rating;

	}

	public Rating checkAndSetUserAndProductForEntity(Rating rating) {
		User user = userService.getUserByID(rating.getRatingId().getCustomerId());
		Product product = productService.getProductByID(rating.getRatingId().getProductId());
		if (user != null && product != null) {
			rating.setUser(user);
			rating.setProduct(product);
			return rating;
		}
		return null;
	}

	@Override
	public List<Rating> getRatingByProductId(long productId) {
		return ratingRepo.findByRatingIdProductId(productId);
	}

	@Override
	public List<Rating> getRatingByCustomerId(long customerId) {
		return ratingRepo.findByRatingIdCustomerId(customerId);
	}

	@Override
	public Rating getRatingByRatingId(RatingId ratingId) {
		return ratingRepo.findById(ratingId).orElse(null);
	}

}
