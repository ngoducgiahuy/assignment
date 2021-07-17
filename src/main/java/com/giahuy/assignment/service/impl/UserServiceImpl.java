package com.giahuy.assignment.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giahuy.assignment.DTO.UserDTO;
import com.giahuy.assignment.common.ERole;
import com.giahuy.assignment.entity.Role;
import com.giahuy.assignment.entity.User;
import com.giahuy.assignment.repository.RoleRepository;
import com.giahuy.assignment.repository.UserRepository;
import com.giahuy.assignment.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByID(long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(long userId, User userNewData) {
		return userRepository.findById(userId).map(user -> {
							user.setAddress(userNewData.getAddress());
							user.setBanned(userNewData.getBanned());
							user.setName(userNewData.getName());
							user.setPhone(userNewData.getPhone());
							user.setRoles(userNewData.getRoles());
							return userRepository.save(user);
		}).orElse(null);
	}

	@Override
	public boolean deleteUserById(long userId) {
		return userRepository.findById(userId).map(user->{
						userRepository.deleteById(userId);
						return true;
		}).orElse(false);
	}

	@Override
	public UserDTO convertToDTO(User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		Set<String> role = new HashSet<>();
		for (Role userRole : user.getRoles()) {
			role.add(userRole.getName().name());
		}
		userDTO.setRole(role);
		return userDTO;
	}
	
	@Override
	public User convertToEntity(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
		Set<Role> roles = this.setRoleForEntity(userDTO.getRole());
		user.setRoles(roles);
		return user;
	}
	

	public Set<Role> setRoleForEntity(Set<String> roleUserDTO) {
		Set<Role> roles = new HashSet<Role>();
		Set<String> strRoles = roleUserDTO;
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role.toLowerCase()) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "staff":
					Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(staffRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
					break;
				}
			});
		}
		return roles;
	}

	
}
