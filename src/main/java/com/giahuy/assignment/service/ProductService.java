package com.giahuy.assignment.service;

import java.util.HashMap;
import java.util.List;

import com.giahuy.assignment.DTO.ProductDTO;
import com.giahuy.assignment.entity.Product;

public interface ProductService {
	public List<Product> getAllProduct();
	
	public Product getProductByID(long productId);
	
	public Product saveProduct(Product product);
	
	public Product updateProduct(long productId, Product product);
	
	public boolean deleteProductById(long productId);

	public ProductDTO convertToDTO(Product product);
	
	public Product convertToEntity(ProductDTO productDTO);
}
