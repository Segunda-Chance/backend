package com.generation.segundachance.exception.produto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Custom exception class for product-related exceptions
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ProductException(String message) {
		super(message);
	}

}
