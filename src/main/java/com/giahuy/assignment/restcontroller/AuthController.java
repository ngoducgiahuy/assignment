package com.giahuy.assignment.restcontroller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giahuy.assignment.common.ERole;
import com.giahuy.assignment.entity.Role;
import com.giahuy.assignment.entity.User;
import com.giahuy.assignment.payload.request.LoginRequest;
import com.giahuy.assignment.payload.request.SignupRequest;
import com.giahuy.assignment.payload.response.JwtResponse;
import com.giahuy.assignment.payload.response.MessageResponse;
import com.giahuy.assignment.repository.RoleRepository;
import com.giahuy.assignment.repository.UserRepository;
import com.giahuy.assignment.security.jwt.JwtUtils;
import com.giahuy.assignment.security.services.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder encoder;
	private JwtUtils jwtUtils;

	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		// authenticate function return true if the input is valid, exeption if invalid and null if it can't decide
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		// Set the principal and more detail for the interface Authentication
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Generate the jwt token through the principal above after parse into UserDetail we have defined
		String jwt = jwtUtils.generateJwtToken(authentication);
		// Get the principal and parse into our defined class named UserDetails
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		// Get roles
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		// Check if username is valid?
		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		// Check if email is valid?
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
		// Create new User Object for register
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()), signupRequest.getPhone(), signupRequest.getAddress(),
				signupRequest.getName());
		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		//Create role for user
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

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

	}
}
