package com.generation.segundachance.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	@GetMapping("/all")
	public ResponseEntity <List<UserDTO>> get(){
		
		List<Usuario> users = usuarioRepository.findAll();
		
		List<UserDTO> usersDTO = users.stream()
				.map(user -> new UserDTO(user.getNomeUsuario(), user.getUsuario(), user.getProdutos()))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(usersDTO);
		// TODO: create ProdutoDTO to return "user" attribute as the userDTO not the complete user object
		
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		return usuarioRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/usuario/{usuario}")
	public ResponseEntity<Usuario> getByUsuario(@PathVariable String usuario) {
		return usuarioRepository.findByUsuario(usuario)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> autenticarUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin){
		
		return usuarioService.autenticarUsuario(usuarioLogin)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public UserDTO post(@RequestBody @Valid Usuario usuario) {
		
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
		UserDTO userDTO = new UserDTO(usuarioCadastrado.get().getNomeUsuario(), 
										usuarioCadastrado.get().getUsuario());
		return userDTO;
		// return a DTO, to protect sensitive information of the User as the Id
	}
	
	@PutMapping("/atualizar")
	public UserDTO put(@Valid @RequestBody Usuario usuario, HttpServletRequest request) throws AccessDeniedException {
		
		String userEmail = (String) request.getAttribute("userName");
		Optional<Usuario> requestingUser = usuarioRepository.findByUsuario(userEmail);
		
		//checks if the user present in the request is the same as the user being updated to grant access
		if(requestingUser.get().getId() == usuario.getId()) {
			Optional<Usuario> updatedUser = usuarioService.atualizarUsuario(usuario);
			UserDTO userDTO = new UserDTO(updatedUser.get().getNomeUsuario(), 
											updatedUser.get().getUsuario());
			return userDTO;
		}else {
	        throw new AccessDeniedException("Você não tem permissão para atualizar este usuário.");
	    }
	}
}
