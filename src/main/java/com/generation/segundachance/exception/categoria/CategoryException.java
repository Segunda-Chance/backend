package com.generation.segundachance.exception.categoria;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Custom exception class for category-related exceptions
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CategoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CategoryException(String message) {
		super(message);
	}

}