package com.generation.segundachance.exception.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Custom exception class for user-related exceptions
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserException(String message) {
		super(message);
	}
}
