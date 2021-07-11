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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.giahuy.assignment.DTO.CategoryDTO;
import com.giahuy.assignment.entity.Category;
import com.giahuy.assignment.exception.DataNotFoundException;
//import com.giahuy.assignment.entity.Product;
import com.giahuy.assignment.service.CategoryService;
//import com.giahuy.assignment.service.ProductService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

//	@Autowired
//	private ModelMapper modelMapper;

//	@Autowired
//	private ProductService productService;

	@GetMapping("/categories")
	public List<CategoryDTO> findAllCategory() {
		List<Category> categories = categoryService.getAllCategory();
		return categories.stream()
					.map(categoryService::convertToDTO)
					.collect(Collectors.toList());
	}

	@GetMapping("/categories/{categoryId}")
	public CategoryDTO findCategoryById(@PathVariable long categoryId) {
		Category categoryResult = categoryService.getCategoryByID(categoryId);
		if(categoryResult==null) {
			throw categoryNotFoundException(categoryId);
		}
		return categoryService.convertToDTO(categoryResult);
		
	}
	
	@GetMapping("/categories/search")
	public List<CategoryDTO> searchCategories(@RequestParam String name){
		List<Category> categoryByName = categoryService.findCategoryByName(name);
		return categoryByName.stream()
							.map(categoryService::convertToDTO)
							.collect(Collectors.toList());
	}

	@PostMapping("/categories")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public CategoryDTO saveCategory(@RequestBody CategoryDTO newCategoryDTO) {
		Category newCategory = categoryService.convertToEntity(newCategoryDTO);
		Category categoryCreated = categoryService.saveCategory(newCategory);
		return categoryService.convertToDTO(categoryCreated);
	}

	@PutMapping("/categories/{categoryId}")
	public CategoryDTO updateCategory(@RequestBody CategoryDTO newDataCategoryDTO, @PathVariable long categoryId) {
		Category newDataCategory = categoryService.convertToEntity(newDataCategoryDTO);
		Category categoryUpdated = categoryService.updateCategory(categoryId, newDataCategory);
		if(categoryUpdated==null) {
			throw categoryNotFoundException(categoryId);
		}
		return categoryService.convertToDTO(categoryUpdated);
	}

	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<Void> deleteCategoryById(@PathVariable long categoryId) {
		boolean result = categoryService.deleteCategoryById(categoryId);
		return (result) ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
	}

	public DataNotFoundException categoryNotFoundException(long categoryId) {
		return new DataNotFoundException("Category with id = " + categoryId + "not found");
	}

}
