package com.giahuy.assignment.DTO;

public class OrderDetailDTO {
	private Long orderId;
	private Long productId;
	private int orderQuantity;
	private float orderPrice;
	private float total;
	
	public OrderDetailDTO() {
	}

	public OrderDetailDTO(Long orderId, Long productId, int orderQuantity, float orderPrice, float total) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.orderQuantity = orderQuantity;
		this.orderPrice = orderPrice;
		this.total = total;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public float getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
}
