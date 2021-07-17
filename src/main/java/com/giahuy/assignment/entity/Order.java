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
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String orderAddress;
	
	private String note;
	
	@Column(name = "total_price")
	private Float totalPrice;
	
	@Column(name="order_date")
	private LocalDateTime orderDate;
	
	@Column(name="received_date")
	private LocalDateTime receivedDate;
	
	private int status;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	@JsonManagedReference(value="order-orderdetail")
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_id")
	@JsonBackReference(value = "user-order")
	private User user;

	public Order() {
	}

	public Order(long id, String orderAddress, String note, Float totalPrice, LocalDateTime orderDate,
			LocalDateTime receivedDate, int status, List<OrderDetail> orderDetails, User user) {
		super();
		this.id = id;
		this.orderAddress = orderAddress;
		this.note = note;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.receivedDate = receivedDate;
		this.status = status;
		this.orderDetails = orderDetails;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(LocalDateTime receivedDate) {
		this.receivedDate = receivedDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
