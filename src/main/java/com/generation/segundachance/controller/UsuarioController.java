package com.generation.segundachance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.segundachance.dto.UserDTO;
import com.generation.segundachance.dto.UserLoginDTO;
import com.generation.segundachance.model.Usuario;
import com.generation.segundachance.model.UsuarioLogin;
import com.generation.segundachance.repository.UsuarioRepository;
import com.generation.segundachance.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired UsuarioService userService;
	
	@GetMapping("/all")
	public ResponseEntity <List<UserDTO>> getAll(){
		List<UserDTO> userDTOs = userService.getAll();
	    return ResponseEntity.ok(userDTOs);
		// TODO: create ProdutoDTO to return "user" attribute as the userDTO not the complete user object
		
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
		UserDTO userDTO = userService.getUserById(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
	}
	
	@GetMapping("/usuario/{usuario}")
	public ResponseEntity<UserDTO> getByUsuario(@PathVariable String usuario) {
		UserDTO userDTO = userService.getUserByUsername(usuario);
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UserLoginDTO> autenticarUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin){
		UserLoginDTO userDTO = usuarioService.authenticateUser(usuarioLogin);
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
		// return a DTO, to protect sensitive information of the User as the Id and password
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<UserDTO> post(@RequestBody @Valid Usuario usuario) {
		UserDTO userDTO = userService.registerUser(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
		// return a DTO, to protect sensitive information of the User as the Id and password
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<UserDTO> put(@Valid @RequestBody Usuario usuario, HttpServletRequest request) {
		UserDTO userDTO = userService.updateUser(usuario, request);
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
		// return a DTO, to protect sensitive information of the User as the Id and password
	}
}
