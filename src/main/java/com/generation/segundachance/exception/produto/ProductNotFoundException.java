package com.generation.segundachance.exception.produto;

public class ProductNotFoundException extends ProductException {
	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(String message) {
		super(message);
	}
}
