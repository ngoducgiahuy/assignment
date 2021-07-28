package com.giahuy.assignment.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "rating")
public class Rating {
	@Embeddable
	public static class RatingId implements Serializable {
		private static final long serialVersionUID = -8961090089148306041L;

		@Column(name = "customer_id", nullable = false, updatable = false)
		private Long customerId;
		
		@Column(name = "product_id", nullable = false, updatable = false)
		private Long productId;
		
		public RatingId() {
			super();
		}
		
		public RatingId(Long customerId, Long productId) {
			this.customerId = customerId;
			this.productId = productId;
		}
		
		public Long getCustomerId() {
			return customerId;
		}

		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this==obj) return true;
			if(obj==null||getClass()!=obj.getClass()) return false;
			RatingId thatRatingId = (RatingId) obj;
			if(!customerId.equals(thatRatingId.customerId)) return false;
			return productId.equals(thatRatingId.productId);
		}
		
		@Override
		public int hashCode() {
			int result = customerId.hashCode();
			result = 24 * result + productId.hashCode();
			return result;
		}
	}
	
	@EmbeddedId
	private RatingId ratingId;
	
	@Column(name="rating_date")
	private LocalDateTime ratingDate;
	
	@Column(name="rating_point")
	private int ratingPoint;
	
	private String comment;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customer_id", insertable = false, updatable = false)
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id", insertable = false, updatable = false)
	@JsonBackReference
	private Product product;
	
	
	
	
	public Rating() {
		super();
	}

	public Rating(RatingId ratingId, LocalDateTime ratingDate, int ratingPoint, String comment, User user,
			Product product) {
		this.ratingId = ratingId;
		this.ratingDate = ratingDate;
		this.ratingPoint = ratingPoint;
		this.comment = comment;
		this.user = user;
		this.product = product;
	}

	public RatingId getRatingId() {
		return ratingId;
	}

	public void setRatingId(RatingId ratingId) {
		this.ratingId = ratingId;
	}

	public LocalDateTime getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(LocalDateTime ratingDate) {
		this.ratingDate = ratingDate;
	}

	public int getRatingPoint() {
		return ratingPoint;
	}

	public void setRatingPoint(int ratingPoint) {
		this.ratingPoint = ratingPoint;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
