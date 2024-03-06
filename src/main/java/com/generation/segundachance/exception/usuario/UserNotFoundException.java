package com.generation.segundachance.exception.usuario;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.generation.segundachance.model.Usuario;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends UserException {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
}
