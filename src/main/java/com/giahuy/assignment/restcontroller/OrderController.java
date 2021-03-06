package com.giahuy.assignment.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.giahuy.assignment.exception.DataNotFoundException;
import com.giahuy.assignment.exception.UserNotFoundExeption;
import com.giahuy.assignment.payload.response.MessageResponse;
import com.giahuy.assignment.service.OrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public ResponseEntity<?> findAllOrder() {
		List<OrderDTO> ordersDto = orderService.getAllOrder().stream().map(orderService::convertToDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<List<OrderDTO>>(ordersDto, HttpStatus.OK);
	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<?> findOrderById(@PathVariable long orderId) {
		Order order = orderService.getOrderByID(orderId);
		if (order == null)
			return ResponseEntity.badRequest().body(new MessageResponse("Order not found!"));
		return new ResponseEntity<OrderDTO>(orderService.convertToDTO(order),HttpStatus.OK);
	}

	@PostMapping("/orders")
	@Transactional
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> saveOrder(@RequestBody OrderDTO orderDTO) {
		Order newOrder = orderService.convertToEntity(orderDTO);
		if (newOrder == null)
			return ResponseEntity.badRequest().body(new MessageResponse("User not found!"));
		;
		boolean updateQuantity = orderService.updateQuantityForProduct(newOrder.getOrderDetails());
		if (!updateQuantity)
			return ResponseEntity.badRequest().body(new MessageResponse("Update product's quantity fail!"));
		Order orderCreated = orderService.saveOrder(newOrder);
		if (orderCreated == null)
			return ResponseEntity.badRequest().body(new MessageResponse("Create order fail!"));
		return new ResponseEntity<OrderDTO>(orderService.convertToDTO(orderCreated), HttpStatus.CREATED);
	}

	@PutMapping("/orders/{orderId}")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN') or hasRole('USER')")
	public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long orderId) {
		Order newOrderData = orderService.convertToEntity(orderDTO);
		if (newOrderData == null)
			throw new UserNotFoundExeption(orderDTO.getCustomerId());
		Order orderUpdated = orderService.updateOrder(orderId, newOrderData);
		if (orderUpdated == null)
			throw new DataNotFoundException("Order with id " + orderId + " is not found!");
		return orderService.convertToDTO(orderUpdated);

	}

	@DeleteMapping("/orders/{orderId}")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public ResponseEntity<Void> deleteOrderById(@PathVariable long orderId) {
		boolean result = orderService.deleteOrderById(orderId);
		return (result) ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
