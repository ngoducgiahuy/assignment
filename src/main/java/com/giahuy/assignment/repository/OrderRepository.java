package com.giahuy.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giahuy.assignment.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
