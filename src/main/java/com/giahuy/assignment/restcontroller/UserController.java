package com.giahuy.assignment.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giahuy.assignment.DTO.UserDTO;
import com.giahuy.assignment.entity.User;
import com.giahuy.assignment.exception.UserNotFoundExeption;
import com.giahuy.assignment.repository.UserRepository;
import com.giahuy.assignment.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<UserDTO> getUsers() {
		List<User> users = userService.getAllUser();
		return users.stream().map(userService::convertToDTO).collect(Collectors.toList());
	}

	@GetMapping("/users/{userId}")
	public UserDTO getUserById(@PathVariable Long userId) {
		User user = userService.getUserByID(userId);
		return userService.convertToDTO(user);
	}

	@PutMapping("/users/{userId}")
	public UserDTO updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userId) {
		User updateUser = userService.updateUser(userId, userService.convertToEntity(userDTO));
		if(updateUser==null) {
			throw new UserNotFoundExeption(userId);
		}
		return userService.convertToDTO(updateUser);
	}
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long userId){
		boolean result = userService.deleteUserById(userId);
		return (result) ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
