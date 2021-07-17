package com.giahuy.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giahuy.assignment.entity.OrderDetail;
import com.giahuy.assignment.entity.OrderDetail.OrderDetailId;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
	List<OrderDetail> findByOrderDetailIdOrderId(long customerId);

	List<OrderDetail> findByOrderDetailIdProductId(long productId);
	
	
}
