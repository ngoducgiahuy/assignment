package com.giahuy.assignment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
	@Embeddable
	public static class OrderDetailId implements Serializable {
		private static final long serialVersionUID = -4667918858513212146L;
		@Column(name = "order_id", nullable = false, updatable = false)
		private Long orderId;
		
		@Column(name = "product_id", nullable = false, updatable = false)
		private Long productId;

		public OrderDetailId() {
		}

		public OrderDetailId(Long orderId, Long productId) {
			this.orderId = orderId;
			this.productId = productId;
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
		
		@Override
		public boolean equals(Object obj) {
			if(this==obj) return true;
			if(obj==null||getClass()!=obj.getClass()) return false;
			OrderDetailId thatOrderDetailId = (OrderDetailId) obj;
			if(!orderId.equals(thatOrderDetailId.orderId)) return false;
			return productId.equals(thatOrderDetailId.productId);
		}
		
		@Override
		public int hashCode() {
			int result = orderId.hashCode();
			result = 24 * result + productId.hashCode();
			return result;
		}
		
	}
	
	@EmbeddedId
	private OrderDetailId orderDetailId;
	
	@Column(name = "order_quantity")
	private int orderQuantity;
	
	@Column(name = "orderPrice")
	private float orderPrice;
	
	private float total;
	
	@MapsId("orderId")
	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="order_id")
	@JsonBackReference(value = "order-orderdetail")
	private Order order;
	
	@MapsId("productId")
	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="product_id")
	@JsonBackReference(value = "product-orderdetail")
	private Product product;

	public OrderDetail() {
	}

	public OrderDetail(OrderDetailId orderDetailId, int orderQuantity, float orderPrice, float total, Order order,
			Product product) {
		super();
		this.orderDetailId = orderDetailId;
		this.orderQuantity = orderQuantity;
		this.orderPrice = orderPrice;
		this.total = total;
		this.order = order;
		this.product = product;
	}

	public OrderDetailId getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(OrderDetailId orderDetailId) {
		this.orderDetailId = orderDetailId;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
