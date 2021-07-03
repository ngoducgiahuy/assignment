package com.giahuy.assignment.restcontroller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giahuy.assignment.entity.Category;
import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.service.CategoryService;
import com.giahuy.assignment.service.ProductService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<Category> findAllCategory(){
		return categoryService.getAllCategory();
	}
	
	@GetMapping("/{categoryId}")
	public Category findCategoryById(@PathVariable long categoryId) {
		return categoryService.getCategoryByID(categoryId);
	}
	
	@PostMapping
	public Category saveCategory(@RequestBody Category category) {
		return categoryService.saveCategory(category);
	}
	
//	@PostMapping("/{categoryId}/products")
//	public Product saveProduct(@RequestBody Product product, @PathVariable long categoryId) {
//		Category categoryNewProduct = categoryService.getCategoryByID(categoryId);
//		product.setCategory(categoryNewProduct);
//		return productService.saveProduct(product);
//	}
	
	@PutMapping("/{categoryId}")
	public Category updateCategory(@RequestBody Category category, @PathVariable long categoryId) {
		return categoryService.updateCategory(categoryId, category);
	}
	
	@DeleteMapping("/{categoryId}")
	public HashMap<String, String> deleteCategoryById(@PathVariable long categoryId) {
		return categoryService.deleteCategoryById(categoryId);
	}
}
