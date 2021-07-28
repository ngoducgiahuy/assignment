package com.giahuy.assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.giahuy.assignment.entity.OrderDetail;
import com.giahuy.assignment.entity.OrderDetail.OrderDetailId;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
	List<OrderDetail> findByOrderDetailIdOrderId(long customerId);

	List<OrderDetail> findByOrderDetailIdProductId(long productId);
	
	@Query(value = "SELECT od.product_id FROM order_detail od WHERE od.product_id = :productId LIMIT 1", nativeQuery = true) 
	Optional<Long> checkIfProductIdExistInOrder(@Param("productId") Long productId);
}
