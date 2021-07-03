package com.giahuy.assignment.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giahuy.assignment.entity.Category;
import com.giahuy.assignment.exception.DataNotFoundException;
import com.giahuy.assignment.repository.CategoryRepository;
import com.giahuy.assignment.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryByID(long categoryId) {
		return categoryRepository.findById(categoryId)
								.orElseThrow(()->categoryNotFoundException(categoryId));
	}

	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(long categoryId, Category categoryNewData) {
		return categoryRepository.findById(categoryId).map(category -> {
				category.setName(categoryNewData.getName());
				category.setDescription(categoryNewData.getDescription());
				return categoryRepository.save(category);
		}).orElseThrow(()->categoryNotFoundException(categoryId));
	}

	@Override
	public HashMap<String, String> deleteCategoryById(long categoryId) {
		HashMap<String, String> result_message = new HashMap<String, String>();
		return categoryRepository.findById(categoryId).map(category -> {
				categoryRepository.delete(category);
				result_message.put("message", "Delete category id " + categoryId + " successfully!");
				return result_message;
		}).orElseThrow(()->categoryNotFoundException(categoryId));
	}
	
	public DataNotFoundException categoryNotFoundException(long categoryId) {
		return new DataNotFoundException("Category with id = " + categoryId + "not found");
	}
	
}
