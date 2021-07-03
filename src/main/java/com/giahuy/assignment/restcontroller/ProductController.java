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

import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public List<Product> getAllProduct(){
		return productService.getAllProduct();
	}
	
	@GetMapping("/products/{productId}")
	public Product getProductById(@PathVariable long productId) {
		return productService.getProductByID(productId);
	}
	
	@PostMapping("/categories/{categoryId}/products")
	public Product saveProduct(@RequestBody Product product, @PathVariable long categoryId) {
		return productService.saveProduct(product, categoryId);
	}
	
	@PutMapping("/categories/{categoryId}/products/{productId}")
	public Product updateProduct(@RequestBody Product product, 
								@PathVariable long productId,
								@PathVariable long categoryId) {
		return productService.updateProduct(categoryId, productId, product);
	}
	
	@DeleteMapping("/categories/{categoryId}/products/{productId}")
	public HashMap<String, String> deleteProductById(@PathVariable long productId){
		return productService.deleteProductById(productId);
	}
}
