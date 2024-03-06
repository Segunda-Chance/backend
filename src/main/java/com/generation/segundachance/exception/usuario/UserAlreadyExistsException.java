package com.generation.segundachance.exception.usuario;

public class UserAlreadyExistsException extends UserException {
	
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
