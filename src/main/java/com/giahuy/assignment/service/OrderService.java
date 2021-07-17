package com.giahuy.assignment.service;

import java.util.List;

import com.giahuy.assignment.DTO.OrderDTO;
import com.giahuy.assignment.entity.Order;
import com.giahuy.assignment.entity.OrderDetail;

public interface OrderService {
public List<Order> getAllOrder();
	
	public Order getOrderByID(long orderId);
	
	public Order saveOrder(Order order);
	
	public Order updateOrder(long orderId, Order orderNewData);
	
	public boolean deleteOrderById(long orderId);
	
	public OrderDTO convertToDTO(Order order);
	
	public Order convertToEntity(OrderDTO orderDTO);
	
	public boolean updateQuantityForProduct(List<OrderDetail> orderDetails);
}
