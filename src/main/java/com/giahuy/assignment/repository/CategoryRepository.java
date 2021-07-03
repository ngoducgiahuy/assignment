package com.giahuy.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giahuy.assignment.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
