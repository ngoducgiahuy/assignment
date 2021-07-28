package com.giahuy.assignment.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user",schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = "email"),
							@UniqueConstraint(columnNames = "username")})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, insertable = false)
	private long id;
	
	@Size(min = 3, max = 20)
	@Column(updatable = false)
	private String username;
	
	@Email
	@Column(updatable = false)
	private String email;
	
	private String password;
	
	private String phone;
	
	private boolean active;
	
	private String address;
	
	@Size(min = 2, max = 80)
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", 
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Rating> ratings = new ArrayList<Rating>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	@JsonManagedReference(value="user-order")
	private List<Order> orders = new ArrayList<Order>();
	
	public User() {
		
	}

	public User(@NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Email String email,
			@NotBlank @Size(min = 6, max = 30) String password, @NotBlank String phone, String address,
			@Size(min = 2, max = 80) String name, boolean active) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.name = name;
		this.active = active;
	}
	
	public User(long id, @Size(min = 3, max = 20) String username, @Email String email, String password, String phone,
			boolean active, String address, @Size(min = 2, max = 80) String name, Set<Role> roles, List<Rating> ratings,
			List<Order> orders) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.active = active;
		this.address = address;
		this.name = name;
		this.roles = roles;
		this.ratings = ratings;
		this.orders = orders;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
