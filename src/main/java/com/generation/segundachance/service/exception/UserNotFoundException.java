package com.generation.segundachance.service.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.generation.segundachance.model.Usuario;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends HttpClientErrorException {

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "O usuário não foi encontrado no banco de dados";
	
	public UserNotFoundException() {
		super(HttpStatus.NOT_FOUND, defaultMessage);
	}
	
	public static void ThrowIfIsEmpty(Optional<Usuario> user) {
		if(user.isEmpty()) {
			throw new UserNotFoundException();
		}
	}
	
}
