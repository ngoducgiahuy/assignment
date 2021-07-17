package com.giahuy.assignment.service;

import java.util.List;

import com.giahuy.assignment.DTO.OrderDetailDTO;
import com.giahuy.assignment.entity.OrderDetail;
import com.giahuy.assignment.entity.OrderDetail.OrderDetailId;

public interface OrderDetailService {

	public OrderDetailDTO convertToDTO(OrderDetail orderDetail);

	public OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO);

	public OrderDetail getOrderDetailByOrderDetailId(OrderDetailId orderDetailId);

	public List<OrderDetail> getOrderDetailByOrderId(long orderId);
}
