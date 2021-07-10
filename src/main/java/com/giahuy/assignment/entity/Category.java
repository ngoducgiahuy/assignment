package com.giahuy.assignment.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private String description;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Product> products = new ArrayList<Product>();

	


	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Category(long id) {
		super();
		this.id = id;
	}



	public Category(long id, String name, String description, List<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.products = products;
	}
	
	

	public Category(long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}





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

	public List<Product> getProducts() {
		return products;
	}
	

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product product) {
		products.add(product);
		product.setCategory(this);
	}
	
	
}
