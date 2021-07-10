package com.giahuy.assignment.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.giahuy.assignment.DTO.ProductDTO;
import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.exception.DataNotFoundException;
import com.giahuy.assignment.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public List<ProductDTO> getAllProduct(){
		List<Product> products = productService.getAllProduct();
		return products.stream()
				.map(productService::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/products/{productId}")
	public ProductDTO getProductById(@PathVariable long productId) {
		Product productResult = productService.getProductByID(productId);
		if(productResult==null) {
			throw productNotFoundException(productId);
		}
		return productService.convertToDTO(productResult);
	}
	
	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ProductDTO saveProduct(@RequestBody ProductDTO newProductDTO) {
		Product newProduct = productService.convertToEntity(newProductDTO);
		if(newProduct==null) {
			throw categoryNotFoundException(newProductDTO.getCategory_id());
		}
		Product newProductCreated = productService.saveProduct(newProduct);
		return productService.convertToDTO(newProductCreated);
	}
	
	@PutMapping("/products/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDTO updateProduct(@RequestBody ProductDTO updateProductDTO, 
								@PathVariable long productId) {
		Product updateProduct = productService.convertToEntity(updateProductDTO);
		if(updateProduct==null) {
			throw categoryNotFoundException(updateProductDTO.getCategory_id());
		}
		Product updatedProduct = productService.updateProduct(productId, updateProduct);
		if(updatedProduct==null) {
			throw productNotFoundException(productId);
		}
		return productService.convertToDTO(updatedProduct);
	}
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Void> deleteProductById(@PathVariable long productId){
		boolean result = productService.deleteProductById(productId);
		return (result) ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	public DataNotFoundException productNotFoundException(long productId) {
		return new DataNotFoundException("Product with id = " + productId + "not found");
	}
	
	public DataNotFoundException categoryNotFoundException(long categoryId) {
		return new DataNotFoundException("Product with id = " + categoryId + "not found");
	}
}
