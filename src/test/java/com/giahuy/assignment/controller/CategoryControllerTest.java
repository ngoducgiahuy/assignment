package com.giahuy.assignment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.giahuy.assignment.entity.Category;
import com.giahuy.assignment.service.CategoryService;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
	@MockBean
	private CategoryService categoryService;

	@Autowired
	private MockMvc mockMvc;

	private List<Category> categories;

	private Category cate1;

	private Category cate2;

	@BeforeEach
	public void setup() {
		cate1 = new Category(1, "Cate 1", "Des 1", null);
		cate1 = new Category(2, "Cate 2", "Des 2", null);
		categories = new ArrayList<Category>();
		categories.add(cate1);
		categories.add(cate2);
	}

	
	@Test
	@DisplayName("Find all and return status 200 and json array")
	public void findAllCategoriesSuccess() throws Exception {
		when(categoryService.getAllCategory()).thenReturn(categories);
		mockMvc.perform(get("/api/categories").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(2)));
	}

	@Test
	@DisplayName("Create new category and return status 201")
	@WithMockUser(username = "huy", password = "123456", roles = "ADMIN")
	public void saveCategorySuccess() throws Exception {
		Category cate3 = new Category(3, "Cate 3", "Des 3", null);
		when(categoryService.saveCategory(cate3)).thenReturn(cate3);
		mockMvc.perform(post("/api/categories").content(parseToJson(cate3)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Create new category and return status 401")
	public void saveCategoryUnauthorized() throws Exception {
		Category cate3 = new Category(3, "Cate 3", "Des 3", null);
		when(categoryService.saveCategory(cate3)).thenReturn(cate3);
		mockMvc.perform(post("/api/categories").content(parseToJson(cate3)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	public String parseToJson(Object obj) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json;
		try {
			json = ow.writeValueAsString(obj);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
