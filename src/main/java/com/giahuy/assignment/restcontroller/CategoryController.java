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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.giahuy.assignment.DTO.CategoryDTO;
import com.giahuy.assignment.entity.Category;
import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.exception.DataNotFoundException;
import com.giahuy.assignment.payload.response.MessageResponse;
//import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.service.CategoryService;
//import com.giahuy.assignment.service.ProductService;
import com.giahuy.assignment.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDTO>> findAllCategory() {
		List<Category> categories = categoryService.getAllCategory();
		List<CategoryDTO> listCateDto = categories.stream()
					.map(categoryService::convertToDTO)
					.collect(Collectors.toList());
		return new ResponseEntity<List<CategoryDTO>>(listCateDto,HttpStatus.OK);
	}

	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<?> findCategoryById(@PathVariable long categoryId) {
		Category categoryResult = categoryService.getCategoryByID(categoryId);
		if(categoryResult==null) {
			return ResponseEntity.badRequest().body(new MessageResponse("The category not found!"));
		}
		return new ResponseEntity<CategoryDTO>(categoryService.convertToDTO(categoryResult),HttpStatus.OK);
		
	}
	
	@GetMapping("/categories/search")
	public ResponseEntity<?> searchCategories(@RequestParam String name){
		Category categoryByName = categoryService.findCategoryByName(name);
		if(categoryByName==null) {
			return ResponseEntity.badRequest().body(new MessageResponse("The category name not found!"));
		}
		return new ResponseEntity<CategoryDTO>(categoryService.convertToDTO(categoryByName),HttpStatus.OK);
		
	}

	@PostMapping("/categories")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO newCategoryDTO) {
		Category getCategoryByName = categoryService.findCategoryByName(newCategoryDTO.getName());
		if(getCategoryByName!=null) return ResponseEntity.badRequest().body(new MessageResponse("The category name has exist!"));
		Category newCategory = categoryService.convertToEntity(newCategoryDTO);
		Category categoryCreated = categoryService.saveCategory(newCategory);
		return new ResponseEntity<CategoryDTO>(categoryService.convertToDTO(categoryCreated),HttpStatus.CREATED);
	}

	@PutMapping("/categories/{categoryId}")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO newDataCategoryDTO, @PathVariable long categoryId) {
		Category getCategoryByName = categoryService.findCategoryByName(newDataCategoryDTO.getName());
		if(getCategoryByName!=null&&getCategoryByName.getId()!=categoryId) {
			return ResponseEntity.badRequest().body(new MessageResponse("The category name has exist!"));
		}
		Category newDataCategory = categoryService.convertToEntity(newDataCategoryDTO);
		Category categoryUpdated = categoryService.updateCategory(categoryId, newDataCategory);
		if(categoryUpdated==null) {
			throw categoryNotFoundException(categoryId);
		}
		return new ResponseEntity<CategoryDTO>(categoryService.convertToDTO(categoryUpdated),HttpStatus.OK);
		
	}

	@DeleteMapping("/categories/{categoryId}")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteCategoryById(@PathVariable long categoryId) {
		List<Product> productByCategoryId = productService.getProductsByCategoryId(categoryId);
		if(productByCategoryId==null) {
			boolean result = categoryService.deleteCategoryById(categoryId);
			return (result) ? new ResponseEntity<Void>(HttpStatus.OK) : ResponseEntity.badRequest().body(new MessageResponse("Category not found!"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Category has products, cannot delete!"));
	}

	public DataNotFoundException categoryNotFoundException(long categoryId) {
		return new DataNotFoundException("Category with id = " + categoryId + "not found");
	}

}
