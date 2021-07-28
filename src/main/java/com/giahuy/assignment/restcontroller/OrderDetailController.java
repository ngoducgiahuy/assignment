package com.giahuy.assignment.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giahuy.assignment.DTO.OrderDetailDTO;
import com.giahuy.assignment.entity.OrderDetail;
import com.giahuy.assignment.entity.OrderDetail.OrderDetailId;
import com.giahuy.assignment.exception.DataNotFoundException;
import com.giahuy.assignment.service.OrderDetailService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderDetailController {
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/orders/{orderId}/products/{productId}")
	public OrderDetailDTO getOrderDetailById(@PathVariable Long orderId, @PathVariable Long productId) {
		OrderDetailId orderDetailId = new OrderDetailId(orderId, productId);
		OrderDetail orderDetail = orderDetailService.getOrderDetailByOrderDetailId(orderDetailId);
		if(orderDetail==null) throw new DataNotFoundException("Order detail not found!");
		return orderDetailService.convertToDTO(orderDetail);
	}
	
	@GetMapping("/orders/{orderId}/orderdetails")
	public List<OrderDetailDTO> getOrderDetailByOrderId(@PathVariable Long orderId){
		List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByOrderId(orderId);
		if(orderDetails==null) throw new DataNotFoundException("Order not found!");
		return orderDetails.stream().map(orderDetailService::convertToDTO).collect(Collectors.toList());
	}
}
