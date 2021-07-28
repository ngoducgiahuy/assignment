package com.giahuy.assignment.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
	private long id;
	private long customerId;
	private String customerUsername;
	private String orderAddress;
	private String note;
	private Float totalPrice;
	private LocalDateTime orderDate;
	private LocalDateTime receivedDate;
	private int status;
	private List<OrderDetailDTO> orderDetails = new ArrayList<OrderDetailDTO>();
	
	

	public OrderDTO(long id, long customerId, String customerUsername, String orderAddress, String note,
			Float totalPrice, LocalDateTime orderDate, LocalDateTime receivedDate, int status,
			List<OrderDetailDTO> orderDetails) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.customerUsername = customerUsername;
		this.orderAddress = orderAddress;
		this.note = note;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.receivedDate = receivedDate;
		this.status = status;
		this.orderDetails = orderDetails;
	}

	public OrderDTO() {
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
	public List<OrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}
}
