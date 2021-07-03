package com.giahuy.assignment.service;

import java.util.HashMap;
import java.util.List;

import com.giahuy.assignment.entity.Category;

public interface CategoryService {
	public List<Category> getAllCategory();
	
	public Category getCategoryByID(long categoryId);
	
	public Category saveCategory(Category category);
	
	public Category updateCategory(long categoryId, Category categoryNewData);
	
	public HashMap<String, String> deleteCategoryById(long categoryId);
}
