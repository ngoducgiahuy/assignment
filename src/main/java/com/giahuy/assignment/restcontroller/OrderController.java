package com.giahuy.assignment.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giahuy.assignment.DTO.OrderDTO;
import com.giahuy.assignment.entity.Order;
import com.giahuy.assignment.exception.CustomException;
import com.giahuy.assignment.exception.DataNotFoundException;
import com.giahuy.assignment.exception.OrderCreateException;
import com.giahuy.assignment.exception.UserNotFoundExeption;
import com.giahuy.assignment.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public List<OrderDTO> findAllOrder() {
		return orderService.getAllOrder().stream().map(orderService::convertToDTO).collect(Collectors.toList());
	}

	@GetMapping("/orders/{orderId}")
	public OrderDTO findOrderById(@PathVariable long orderId) {
		Order order = orderService.getOrderByID(orderId);
		return orderService.convertToDTO(order);
	}
	
	@PostMapping("/orders")
	@Transactional
	public OrderDTO saveOrder(@RequestBody OrderDTO orderDTO) {
		Order newOrder = orderService.convertToEntity(orderDTO);
		if(newOrder==null) throw new OrderCreateException();
		boolean updateQuantity = orderService.updateQuantityForProduct(newOrder.getOrderDetails());
		if(!updateQuantity) throw new CustomException("Update quantity for product fail!");
		Order orderCreated = orderService.saveOrder(newOrder);
		return orderService.convertToDTO(orderCreated);
	}
	
	@PutMapping("/orders/{orderId}")
	public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long orderId) {
		Order newOrderData = orderService.convertToEntity(orderDTO);
		if(newOrderData==null) throw new UserNotFoundExeption(orderDTO.getCustomerId());
		Order orderUpdated = orderService.updateOrder(orderId, newOrderData);
		if(orderUpdated==null) throw new DataNotFoundException("Order with id "+ orderId + " is not found!");
		return orderService.convertToDTO(orderUpdated);
		
	}
	
	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<Void> deleteOrderById(@PathVariable long orderId){
		boolean result = orderService.deleteOrderById(orderId);
		return (result) ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	} 
}
