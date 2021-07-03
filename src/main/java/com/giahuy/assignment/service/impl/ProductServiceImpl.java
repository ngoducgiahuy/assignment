package com.giahuy.assignment.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.exception.DataNotFoundException;
import com.giahuy.assignment.repository.CategoryRepository;
import com.giahuy.assignment.repository.ProductRepository;
import com.giahuy.assignment.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductByID(long productId) {
		return productRepository.findById(productId)
								.orElseThrow(()->productNotFoundException(productId));
	}

	@Override
	public Product saveProduct(Product product, long categoryId) {
		return categoryRepository.findById(categoryId).map(category -> {
								product.setCategory(category);
								return productRepository.save(product);
		}).orElseThrow(()->new DataNotFoundException("Category id "+categoryId+"not exist!"));
	}

	@Override
	public Product updateProduct(long categoryId, long productId, Product productNewData) {
		if(!categoryRepository.existsById(categoryId)) {
			throw new DataNotFoundException("Category id "+categoryId+"not exist!");
		}
		
		return productRepository.findById(productId).map(product -> {
								product.setCreatedDate(productNewData.getCreatedDate());
								product.setDescription(productNewData.getDescription());
								product.setImage(productNewData.getImage());
								product.setName(productNewData.getName());
								product.setPrice(productNewData.getPrice());
								product.setRatingPoint(productNewData.getRatingPoint());
								product.setUpdatedDate(LocalDateTime.now());
								return productRepository.save(product);
		}).orElseThrow(()->productNotFoundException(productId));	
	}

	@Override
	public HashMap<String, String> deleteProductById(long productId) {
		HashMap<String, String> resultMessage = new HashMap<String, String>();
		return productRepository.findById(productId).map(product -> {
								productRepository.delete(product);
								resultMessage.put("message", "Delete product id " + productId + " successfully!");
								return resultMessage;
		}).orElseThrow(()->productNotFoundException(productId));
	}
	
	public DataNotFoundException productNotFoundException(long productId) {
		return new DataNotFoundException("Product with id = " + productId + "not found");
	}
	

	
}
