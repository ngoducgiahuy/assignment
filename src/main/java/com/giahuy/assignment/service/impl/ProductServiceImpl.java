package com.giahuy.assignment.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giahuy.assignment.DTO.ProductDTO;
import com.giahuy.assignment.entity.Category;
import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.exception.DataNotFoundException;
import com.giahuy.assignment.repository.ProductRepository;
import com.giahuy.assignment.service.CategoryService;
import com.giahuy.assignment.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductByID(long productId) {
		return productRepository.findById(productId)
								.orElse(null);
	}

	@Override
	public Product saveProduct(Product product) {
		product.setCreatedDate(LocalDateTime.now());
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(long productId, Product productNewData) {
//		return productRepository.findById(productId).map(product -> {
//								product.setCreatedDate(productNewData.getCreatedDate());
//								product.setDescription(productNewData.getDescription());
//								product.setCategory(productNewData.getCategory());
//								product.setImage(productNewData.getImage());
//								product.setName(productNewData.getName());
//								product.setPrice(productNewData.getPrice());
//								product.setRatingPoint(productNewData.getRatingPoint());
//								product.setUpdatedDate(LocalDateTime.now());
//								return productRepository.save(product);
//		}).orElse(null);
		if(productRepository.existsById(productId)) {
			productNewData.setId(productId);
			productNewData.setUpdatedDate(LocalDateTime.now());
			return productRepository.save(productNewData);
		}
		return null;
	}

	@Override
	public boolean deleteProductById(long productId) {
		return productRepository.findById(productId).map(product -> {
								productRepository.delete(product);
								return true;
		}).orElse(false);
	}
	
	public DataNotFoundException productNotFoundException(long productId) {
		return new DataNotFoundException("Product with id = " + productId + "not found");
	}

	@Override
	public ProductDTO convertToDTO(Product product) {
		ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
		productDTO.setCategory_id(product.getCategory().getId());
		return productDTO;
	}

	@Override
	public Product convertToEntity(ProductDTO productDTO) {
		Product product = modelMapper.map(productDTO, Product.class);
		Category categoryForProduct = categoryService.getCategoryByID(productDTO.getCategory_id());
		if(categoryForProduct==null) {
			return null;
		}
		product.setCategory(categoryForProduct);
		return product;
	}

	@Override
	public List<Product> getProductsByCategoryId(long categoryId) {
		List<Product> resultList = productRepository.findByCategory_Id(categoryId);
		return (resultList.isEmpty()) ? null : resultList;
	}
	

	
}
