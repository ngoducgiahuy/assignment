package com.giahuy.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.giahuy.assignment.entity.Category;
import com.giahuy.assignment.repository.CategoryRepository;

@SpringBootTest
public class CategoryServiceTest {
	@MockBean
	private CategoryRepository cateRepo;
	
	@Autowired
	private CategoryService cateService;
	
	@Test
	@DisplayName("Find all and return array list")
	public void getAllCategorySuccess() {
		Category cate1 = new Category(1, "Trinh Tham", "Truyen trinh tham", null);
		Category cate2 = new Category(2, "Truyen tranh", "Truyen tranh danh cho thieu nhi", null);
		Category cate3 = new Category(3, "Tieu Thuyet", "Truyen tieu thuyet", null);
		
		List<Category> listCate = new ArrayList<Category>();
		
		listCate.add(cate3);
		listCate.add(cate2);
		listCate.add(cate1);
		
		when(cateService.getAllCategory()).thenReturn(listCate);
		
		List<Category> cateList = cateService.getAllCategory();
		assertEquals(3, cateList.size());
	}
	
	@Test
	@DisplayName("Create new Category and return itself")
	public void saveCategorySuccess() {
		Category cate1 = new Category(1, "Trinh Tham", "Truyen trinh tham", null);
		
		when(cateService.saveCategory(cate1)).thenReturn(cate1);
		
		Category cate2 = cateService.saveCategory(cate1);
		assertEquals(cate1, cate2);
	}
}
