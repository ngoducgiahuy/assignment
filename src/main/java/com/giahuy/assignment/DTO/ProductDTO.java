package com.giahuy.assignment.DTO;

import java.time.LocalDateTime;

import javax.persistence.Lob;

import org.hibernate.annotations.Type;

public class ProductDTO {
	private long id;
	private String name;
	private long category_id;
	private String description;
	private Float price;
	private Float ratingPoint;
	private int quantity;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] image;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getRatingPoint() {
		return ratingPoint;
	}
	public void setRatingPoint(Float ratingPoint) {
		this.ratingPoint = ratingPoint;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
}
