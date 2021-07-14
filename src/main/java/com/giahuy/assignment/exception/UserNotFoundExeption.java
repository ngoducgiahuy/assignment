package com.giahuy.assignment.exception;

public class UserNotFoundExeption extends RuntimeException{

	public UserNotFoundExeption(Long userId) {
		super("User with id "+userId+" not found!");
		// TODO Auto-generated constructor stub
	}

}
