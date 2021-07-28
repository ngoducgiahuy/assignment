package com.giahuy.assignment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giahuy.assignment.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Optional<Category> findByName(String name);
}
