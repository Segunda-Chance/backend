package com.generation.segundachance.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcedingProductPropertiesException extends HttpClientErrorException {
	
	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = " não pode exceder o limite de ";
	
	public ExcedingProductPropertiesException(String propertyName, int maxLength) {
		super(HttpStatus.BAD_REQUEST, propertyName + defaultMessage + maxLength + " caracteres");
	}
	
	public static void ThrowIfDataIsTooLong(String propertyName, String propertyValue, int maxLength) {
		if (StringUtils.hasText(propertyValue) && propertyValue.length() > maxLength) {
	        throw new ExcedingProductPropertiesException(propertyName, maxLength);
	    }
	}
}
