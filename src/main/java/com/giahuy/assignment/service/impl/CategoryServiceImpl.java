package com.giahuy.assignment.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giahuy.assignment.DTO.CategoryDTO;
import com.giahuy.assignment.entity.Category;
import com.giahuy.assignment.repository.CategoryRepository;
import com.giahuy.assignment.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryByID(long categoryId) {
		return categoryRepository.findById(categoryId).orElse(null);
	}

	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(long categoryId, Category categoryNewData) {
		if(categoryRepository.existsById(categoryId)) {
			categoryNewData.setId(categoryId);
			return categoryRepository.save(categoryNewData);
		}
		return null;
	}

	@Override
	public boolean deleteCategoryById(long categoryId) {
		return categoryRepository.findById(categoryId).map(category -> {
				categoryRepository.delete(category);
				return true;
		}).orElse(false);
	}
	
	@Override
	public CategoryDTO convertToDTO(Category category) {
		CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
		return categoryDTO;
	}
	
	@Override
	public Category convertToEntity(CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		return category;
	}

	@Override
	public List<Category> findCategoryByName(String name) {
		List<Category> categoriesByName = categoryRepository.findByName(name);
		return (categoriesByName.isEmpty()) ? null : categoriesByName;
	}
	
	
	
}
