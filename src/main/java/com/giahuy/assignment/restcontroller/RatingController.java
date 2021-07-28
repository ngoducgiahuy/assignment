package com.giahuy.assignment.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.entity.Rating;
import com.giahuy.assignment.entity.Rating.RatingId;
import com.giahuy.assignment.payload.response.MessageResponse;
import com.giahuy.assignment.repository.RatingRepository;
import com.giahuy.assignment.service.ProductService;
import com.giahuy.assignment.service.RatingService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RatingController {

	@Autowired
	RatingService ratingService;

	@Autowired
	RatingRepository ratingRepo;
	
	@Autowired
	ProductService productService;

	@GetMapping("/ratings")
	public ResponseEntity<?> getRatings() {
		List<Rating> ratings = ratingService.getAllRating();
		List<RatingDTO> ratingsDto =  ratings.stream().map(ratingService::convertToDTO).collect(Collectors.toList());
		return new ResponseEntity<List<RatingDTO>>(ratingsDto,HttpStatus.OK);
	}

	@GetMapping("/ratings/{productId}")
	public ResponseEntity<?> getRatingsAvg(@PathVariable long productId) {
		Float ratingAvg = ratingService.getRatingAvg(productId);
		return (ratingAvg == null)
				? ResponseEntity.badRequest().body(new MessageResponse("Ratings for product " + productId + " not found"))
				: new ResponseEntity<Float>(ratingAvg, HttpStatus.OK);
	}

	@GetMapping("/ratings/search")
	public ResponseEntity<?> searchRatings(@RequestParam(required = false) Long productId,
			@RequestParam(required = false) Long customerId) {

		if (productId != null && customerId != null) {
			RatingId ratingId = new RatingId(customerId, productId);
			Rating rating = ratingService.getRatingByRatingId(ratingId);
			if(rating!=null) {
				return new ResponseEntity<RatingDTO>(ratingService.convertToDTO(rating),HttpStatus.OK);
			}
			return ResponseEntity.badRequest().body(new MessageResponse("Rating not found"));
		}
		if (productId != null) {
			List<Rating> ratingList = ratingService.getRatingByProductId(productId);
			if(!ratingList.isEmpty()) {
				List<RatingDTO> ratingsDto = ratingList.stream().map(ratingService::convertToDTO).collect(Collectors.toList());
				return new ResponseEntity<List<RatingDTO>>(ratingsDto,HttpStatus.OK);
			}
			return ResponseEntity.badRequest().body(new MessageResponse("Ratings for product " + productId + " not found"));
		}
		if (customerId != null) {
			List<Rating> ratingList = ratingService.getRatingByCustomerId(customerId);
			if(!ratingList.isEmpty()) {
				List<RatingDTO> ratingsDto = ratingList.stream().map(ratingService::convertToDTO).collect(Collectors.toList());
				return new ResponseEntity<List<RatingDTO>>(ratingsDto,HttpStatus.OK);
			}
			return ResponseEntity.badRequest().body(new MessageResponse("Ratings for customer " + customerId + " not found"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Request fail!"));

	}

	@PostMapping("/ratings")
	public ResponseEntity<?> saveRating(@RequestBody RatingDTO ratingDTO) {
		Rating rating = ratingService.convertToEntity(ratingDTO);
		Rating ratingCreated = ratingService.saveRating(rating);
		if (ratingCreated == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Product or user not exist!"));
		}
		Float productRatingAvg = ratingService.getRatingAvg(ratingDTO.getProductId());
		if (productRatingAvg==null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Cannot update rating point average. Create rating fail!"));
		}
		Product product = productService.getProductByID(ratingDTO.getProductId());
		product.setRatingPoint(productRatingAvg);
		productService.saveProduct(product);
		
		RatingDTO ratingDto = ratingService.convertToDTO(ratingCreated);
		return new ResponseEntity<RatingDTO>(ratingDto,HttpStatus.OK);
	}

	@PutMapping("/ratings/{productId}/customer/{customerId}")
	public ResponseEntity<?> updateRating(@RequestBody RatingDTO ratingDTO, @PathVariable long productId,
			@PathVariable long customerId) {
		ratingDTO.setProductId(productId);
		ratingDTO.setCustomerId(customerId);
		Rating rating = ratingService.convertToEntity(ratingDTO);
		Rating ratingUpdated = ratingService.updateRating(rating.getRatingId(), rating);
		if (ratingUpdated == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Rating is not exist!"));
		}
		Float productRatingAvg = ratingService.getRatingAvg(ratingDTO.getProductId());
		if (productRatingAvg==null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Cannot update rating point average. Create rating fail!"));
		}
		Product product = productService.getProductByID(ratingDTO.getProductId());
		product.setRatingPoint(productRatingAvg);
		productService.saveProduct(product);
		
		RatingDTO ratingDto = ratingService.convertToDTO(ratingUpdated);
		return new ResponseEntity<RatingDTO>(ratingDto,HttpStatus.OK);
	}

	@DeleteMapping("/ratings/{productId}/customer/{customerId}")
	public ResponseEntity<Void> deleteRatingById(@PathVariable long productId, @PathVariable long customerId) {
		RatingId ratingId = new RatingId(customerId, productId);
		boolean result = ratingService.deleteRatingById(ratingId);
		return (result) ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
