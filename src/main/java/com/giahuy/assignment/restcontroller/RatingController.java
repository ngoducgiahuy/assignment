package com.giahuy.assignment.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giahuy.assignment.DTO.RatingDTO;
import com.giahuy.assignment.entity.Rating;
import com.giahuy.assignment.entity.Rating.RatingId;
import com.giahuy.assignment.exception.DataNotFoundException;
import com.giahuy.assignment.exception.RatingCreateException;
import com.giahuy.assignment.repository.RatingRepository;
import com.giahuy.assignment.service.RatingService;

@RestController
@RequestMapping("/api")
public class RatingController {

	@Autowired
	RatingService ratingService;
	
	@Autowired
	RatingRepository ratingRepo;

	@GetMapping("/ratings")
	public List<RatingDTO> getRatings() {
		List<Rating> ratings = ratingService.getAllRating();
		return ratings.stream().map(ratingService::convertToDTO).collect(Collectors.toList());
	}

	@GetMapping("/ratings/search")
	public List<RatingDTO> searchRatings(@RequestParam(required = false) Long productId,
			@RequestParam(required = false) Long customerId) {

		if (productId != null && customerId != null) {
			List<RatingDTO> ratings = new ArrayList<RatingDTO>();
			Rating rating = ratingService.searchByProductIdAndCustomerId(productId, customerId);
			ratings.add(ratingService.convertToDTO(rating));
			return ratings;
		}
		if (productId != null) {
			List<Rating> ratingList = ratingService.searchByProductId(productId);
			return ratingList.stream().map(ratingService::convertToDTO).collect(Collectors.toList());
		}
		if (customerId != null) {
			List<Rating> ratingList = ratingService.searchByCustomerId(customerId);
			return ratingList.stream().map(ratingService::convertToDTO).collect(Collectors.toList());
		}
		return null;

	}

	@PostMapping("/ratings")
	public RatingDTO saveRating(@RequestBody RatingDTO ratingDTO) {
		Rating rating = ratingService.convertToEntity(ratingDTO);
		Rating ratingCreated = ratingService.saveRating(rating);
		if (ratingCreated == null) {
			throw new RatingCreateException();
		}
		return ratingService.convertToDTO(ratingCreated);
	}
	
	@PutMapping("/ratings/{productId}/customer/{customerId}")
	public RatingDTO updateRating(@RequestBody RatingDTO ratingDTO,@PathVariable long productId, @PathVariable long customerId) {
		ratingDTO.setProductId(productId);
		ratingDTO.setCustomerId(customerId);
		Rating rating = ratingService.convertToEntity(ratingDTO);
		Rating ratingUpdated = ratingService.updateRating(rating.getRatingId(), rating);
		if(ratingUpdated==null) {
			throw new DataNotFoundException("Rating is not exist!");
		}
		return ratingService.convertToDTO(ratingUpdated);
	}
	
	@DeleteMapping("/ratings/{productId}/customer/{customerId}")
	public ResponseEntity<Void> deleteRatingById(@PathVariable long productId, @PathVariable long customerId){
		RatingId ratingId = new RatingId(customerId, productId);
		boolean result = ratingService.deleteRatingById(ratingId);
		return (result) ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	

}
