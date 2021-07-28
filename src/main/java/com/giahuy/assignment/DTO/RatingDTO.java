package com.giahuy.assignment.DTO;

import java.time.LocalDateTime;

public class RatingDTO {
	private long customerId;
	private String customerName;
	private String customerUserName;
	private long productId;
	private LocalDateTime ratingDate;
	private int ratingPoint;
	private String comment;
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerUserName() {
		return customerUserName;
	}
	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}
	
	
}
