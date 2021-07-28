package com.giahuy.assignment.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.giahuy.assignment.payload.response.MessageResponse;
import com.giahuy.assignment.service.OrderDetailService;
import com.giahuy.assignment.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {
	@Autowired
	private ProductService productService;

	@Autowired
	private OrderDetailService orderDetailService;

	@GetMapping("/products")
	public ResponseEntity<?> getAllProduct() {
		List<Product> products = productService.getAllProduct();
		List<ProductDTO> listProductDTO = products.stream().map(productService::convertToDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<List<ProductDTO>>(listProductDTO, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable long productId) {
		Product productResult = productService.getProductByID(productId);
		if (productResult == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Product not found!"));
		}
		return new ResponseEntity<ProductDTO>(productService.convertToDTO(productResult), HttpStatus.OK);
	}

	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public ResponseEntity<?> saveProduct(@RequestBody ProductDTO newProductDTO) {
		Product getProductByName = productService.findProductByName(newProductDTO.getName());
		if(getProductByName!=null) return ResponseEntity.badRequest().body(new MessageResponse("The product name has exist!"));
		Product newProduct = productService.convertToEntity(newProductDTO);
		if (newProduct == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Category not found!"));
		}
		Product newProductCreated = productService.saveProduct(newProduct);
		return new ResponseEntity<ProductDTO>(productService.convertToDTO(newProductCreated), HttpStatus.CREATED);
	}

	@PutMapping("/products/{productId}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO updateProductDTO, @PathVariable long productId) {
		Product getProductByName = productService.findProductByName(updateProductDTO.getName());
		if(getProductByName!=null&&getProductByName.getId()!=productId) {
			return ResponseEntity.badRequest().body(new MessageResponse("The product name has exist!"));
		}

		Product updateProduct = productService.convertToEntity(updateProductDTO);
		if (updateProduct == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Category not found!"));
		}
		Product updatedProduct = productService.updateProduct(productId, updateProduct);
		if (updatedProduct == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Product not found!"));
		}

		return new ResponseEntity<ProductDTO>(productService.convertToDTO(updatedProduct), HttpStatus.OK);
	}

	@DeleteMapping("/products/{productId}")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteProductById(@PathVariable long productId) {
		boolean checkIfProductExistInOrder = orderDetailService.checkIfProductIdExistInOrder(productId);
		if (checkIfProductExistInOrder == true)
			return ResponseEntity.badRequest().body(new MessageResponse("The product has been ordered, cannot delete!"));
		
		boolean result = productService.deleteProductById(productId);
		return (result) ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	public DataNotFoundException productNotFoundException(long productId) {
		return new DataNotFoundException("Product with id = " + productId + "not found");
	}

	public DataNotFoundException categoryNotFoundException(long categoryId) {
		return new DataNotFoundException("Product with id = " + categoryId + "not found");
	}

	@GetMapping("/categories/{categoryId}/products")
	public List<ProductDTO> getProductsByCategoryId(@PathVariable long categoryId) {
		List<Product> resultList = productService.getProductsByCategoryId(categoryId);
		if (resultList == null)
			throw new DataNotFoundException("Product for cate id" + categoryId + " not found");
		return resultList.stream().map(productService::convertToDTO).collect(Collectors.toList());
	}
}
