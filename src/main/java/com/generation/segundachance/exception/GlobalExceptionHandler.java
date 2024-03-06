package com.generation.segundachance.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.generation.segundachance.exception.categoria.CategoryException;
import com.generation.segundachance.exception.produto.ProductException;
import com.generation.segundachance.exception.usuario.UserException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// provide a customized response for validation errors and return the default
	// message of the first error at the response body
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ResponseEntity.badRequest()
				.body("Erro de validação: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}

	@ResponseBody
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Map<String, String> handleAccessDeniedException(AccessDeniedException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("message", "Você não tem permissão para realizar esta ação.");
		return errorResponse;
	}

	// Handle all exceptions related to {@link UserException} and return the message
	// in the response body.
	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> handleUserException(UserException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	// Handle all exceptions related to {@link CategoryException} and return the
	// message in the response body.
	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<String> handleCategoryException(CategoryException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	// Handle all exceptions related to {@link ProductException} and return the
	// message in the response body.
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<String> handleProductException(ProductException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
