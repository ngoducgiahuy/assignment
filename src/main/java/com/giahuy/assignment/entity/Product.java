package com.giahuy.assignment.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	@JsonBackReference
	private Category category;
	
	private String description;
	
	private Float price;
	
	@Column(name="rating_point")
	private Float ratingPoint;
	
	private String image;
	
	private int quantity;
	
	@Column(name="created_date")
	private LocalDateTime createdDate;	
	
	@Column(name="updated_date")
	private LocalDateTime updatedDate;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Rating> ratings = new ArrayList<Rating>();
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	@JsonManagedReference(value="product-orderdetail")
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	public Product() {
	}

	public Product(long id, String name, Category category, String description, Float price, Float ratingPoint,
			String image, int quantity, LocalDateTime createdDate, LocalDateTime updatedDate, List<Rating> ratings,
			List<OrderDetail> orderDetails) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.ratingPoint = ratingPoint;
		this.image = image;
		this.quantity = quantity;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.ratings = ratings;
		this.orderDetails = orderDetails;
	}

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public void setUpdatedDate(LocalDateTime localDateTime) {
		this.updatedDate = localDateTime;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
