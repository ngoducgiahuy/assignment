package com.giahuy.assignment.service;

import java.util.List;

import com.giahuy.assignment.DTO.UserDTO;
import com.giahuy.assignment.entity.User;

public interface UserService {
	public List<User> getAllUser();

	public User getUserByID(long userId);

	public User saveUser(User user);

	public User updateUser(long userId, User user);

	public boolean deleteUserById(long userId);

	public UserDTO convertToDTO(User user);

	public User convertToEntity(UserDTO userDTO);
}
