package com.giahuy.assignment.exception;

public class UserNotFoundExeption extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundExeption(Long userId) {
		super("User with id "+userId+" not found!");
		// TODO Auto-generated constructor stub
	}

}
