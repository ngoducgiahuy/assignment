package com.giahuy.assignment.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giahuy.assignment.DTO.OrderDetailDTO;
import com.giahuy.assignment.entity.OrderDetail;
import com.giahuy.assignment.entity.OrderDetail.OrderDetailId;
import com.giahuy.assignment.repository.OrderDetailRepository;
import com.giahuy.assignment.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	
	@Autowired
	OrderDetailRepository orderDetailRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
		OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
		orderDetailDTO.setOrderId(orderDetail.getOrderDetailId().getOrderId());
		orderDetailDTO.setProductId(orderDetail.getOrderDetailId().getProductId());
		return orderDetailDTO;
	}

	@Override
	public OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
		OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
		OrderDetailId orderDetailId = new OrderDetailId(orderDetailDTO.getOrderId(), orderDetailDTO.getProductId());
		orderDetail.setOrderDetailId(orderDetailId);
		return orderDetail;
	}

	@Override
	public OrderDetail getOrderDetailByOrderDetailId(OrderDetailId orderDetailId) {
		return orderDetailRepo.findById(orderDetailId).orElse(null);
	}

	@Override
	public List<OrderDetail> getOrderDetailByOrderId(long orderId) {
		return orderDetailRepo.findByOrderDetailIdOrderId(orderId);
	}
	
}
