package com.giahuy.assignment.service;

import java.util.HashMap;
import java.util.List;

import com.giahuy.assignment.entity.Product;

public interface ProductService {
	public List<Product> getAllProduct();
	
	public Product getProductByID(long productId);
	
	public Product saveProduct(Product product, long categoryId);
	
	public Product updateProduct(long categoryId, long productId, Product product);
	
	public HashMap<String, String> deleteProductById(long productId);
}
