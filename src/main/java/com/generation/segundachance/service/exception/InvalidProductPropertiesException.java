package com.generation.segundachance.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidProductPropertiesException extends HttpClientErrorException {
	
	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = " não pode ser vazio.";
	
	public InvalidProductPropertiesException(String propertyName) {
		super(HttpStatus.BAD_REQUEST, propertyName + defaultMessage);
	}
	
	public static void ThrowIfIsEmpty(String propertyName, String propertyValue) {
		if (!StringUtils.hasText(propertyValue)) {
	        throw new InvalidProductPropertiesException(propertyName);
	    }
	}
}
