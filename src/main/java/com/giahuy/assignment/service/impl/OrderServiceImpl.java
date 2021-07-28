package com.giahuy.assignment.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.giahuy.assignment.DTO.OrderDTO;
import com.giahuy.assignment.DTO.OrderDetailDTO;
import com.giahuy.assignment.entity.Order;
import com.giahuy.assignment.entity.OrderDetail;
import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.entity.User;
import com.giahuy.assignment.repository.OrderRepository;
import com.giahuy.assignment.service.OrderDetailService;
import com.giahuy.assignment.service.OrderService;
import com.giahuy.assignment.service.ProductService;
import com.giahuy.assignment.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Override
	public List<Order> getAllOrder() {
		return orderRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public Order getOrderByID(long orderId) {
		return orderRepo.findById(orderId).orElse(null);
	}

	@Override
	public Order saveOrder(Order order) {
		if (order.getOrderDetails() == null)
			return null;
		List<OrderDetail> orderDetails = order.getOrderDetails();
		for (OrderDetail orderDetail : orderDetails) {
			Product product = productService.getProductByID(orderDetail.getOrderDetailId().getProductId());
			if (product == null)
				return null;
			orderDetail.setOrder(order);
			orderDetail.setProduct(product);
		}
		order.setOrderDetails(orderDetails);
		order.setOrderDate(LocalDateTime.now());
		return orderRepo.save(order);
	}

	@Override
	public Order updateOrder(long orderId, Order orderNewData) {
		return orderRepo.findById(orderId).map(order -> {
			order.setNote(orderNewData.getNote());
			order.setOrderAddress(orderNewData.getOrderAddress());
			order.setOrderDate(orderNewData.getOrderDate());
			order.setReceivedDate(order.getReceivedDate());
			order.setStatus(orderNewData.getStatus());
			order.setTotalPrice(order.getTotalPrice());
			return orderRepo.save(order);
		}).orElse(null);
	}

	@Override
	public boolean deleteOrderById(long orderId) {
		return orderRepo.findById(orderId).map(order -> {
			orderRepo.delete(order);
			return true;
		}).orElse(false);
	}

	@Override
	public OrderDTO convertToDTO(Order orderEntity) {
		OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);
		orderDTO.setCustomerId(orderEntity.getUser().getId());
		orderDTO.setCustomerUsername(orderEntity.getUser().getUsername());
		return orderDTO;
	}

	@Override
	public Order convertToEntity(OrderDTO orderDTO) {
		User user = userService.getUserByID(orderDTO.getCustomerId());
		if (user == null)
			return null;
		List<OrderDetailDTO> orderDetailDto = orderDTO.getOrderDetails();
		List<OrderDetail> orderDetails = orderDetailDto.stream().map(orderDetailService::convertToEntity)
				.collect(Collectors.toList());
		Order order = modelMapper.map(orderDTO, Order.class);
		order.setOrderDetails(orderDetails);
		order.setUser(user);
		return order;
	}

	@Override
	public boolean updateQuantityForProduct(List<OrderDetail> orderDetails) {
		for (OrderDetail orderDetail : orderDetails) {
			Product product = productService.getProductByID(orderDetail.getOrderDetailId().getProductId());
			if (product == null)
				return false;
			int productQuantity = product.getQuantity();
			if (productQuantity < orderDetail.getOrderQuantity())
				return false;
			productQuantity -= orderDetail.getOrderQuantity();
			product.setQuantity(productQuantity);
			productService.updateProduct(orderDetail.getOrderDetailId().getProductId(), product);
		}
		return true;
	}

}
