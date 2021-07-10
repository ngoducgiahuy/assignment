package com.giahuy.assignment.DTO;

//import lombok.Getter;
//import lombok.Setter;


public class CategoryDTO {
	private long id;
	private String name;
	private String description;
//	private List<ProductList> products;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
