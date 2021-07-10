package com.giahuy.assignment.service;

import java.util.List;

import com.giahuy.assignment.DTO.CategoryDTO;
import com.giahuy.assignment.entity.Category;

public interface CategoryService {
	public List<Category> getAllCategory();
	
	public Category getCategoryByID(long categoryId);
	
	public Category saveCategory(Category category);
	
	public Category updateCategory(long categoryId, Category categoryNewData);
	
	public boolean deleteCategoryById(long categoryId);
	
	public CategoryDTO convertToDTO(Category category);
	
	public Category convertToEntity(CategoryDTO categoryDTO);
}
