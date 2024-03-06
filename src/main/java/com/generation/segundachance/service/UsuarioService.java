package com.generation.segundachance.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.generation.segundachance.dto.UserDTO;
import com.generation.segundachance.dto.UserLoginDTO;
import com.generation.segundachance.exception.usuario.UserAlreadyExistsException;
import com.generation.segundachance.exception.usuario.UserNotFoundException;
import com.generation.segundachance.mapper.UserMapper;
import com.generation.segundachance.model.Usuario;
import com.generation.segundachance.model.UsuarioLogin;
import com.generation.segundachance.repository.UsuarioRepository;
import com.generation.segundachance.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserMapper userMapper;

	public List<UserDTO> getAll() {
		List<Usuario> entityUsers = usuarioRepository.findAll();
		List<UserDTO> usersDTO = entityUsers.stream().map(userMapper::toDTO).collect(Collectors.toList());
		return usersDTO;
	}

	public UserDTO getUserById(@PathVariable Long id) {
		var userEntity = usuarioRepository.findById(id);
		if (userEntity.isEmpty())
			throw new UserNotFoundException("Id não encontrado");
		return userMapper.toDTO(userEntity.get());
	}

	public UserDTO getUserByUsername(@PathVariable String username) {
		var userEntity = usuarioRepository.findByUsuario(username);
		if (userEntity.isEmpty())
			throw new UserNotFoundException("Email não encontrado");
		return userMapper.toDTO(userEntity.get());
	}

	public UserDTO registerUser(Usuario user) {
		// check if a user with the same username already exists in the database
		// and throws a custom exception if it does
		Optional<Usuario> existingUser = usuarioRepository.findByUsuario(user.getUsuario());
		if (existingUser.isPresent()) {
			throw new UserAlreadyExistsException("O usuário já existe");
		}
		// encrypts the password of the user object
		user.setSenha(criptografarSenha(user.getSenha()));

		// persists the object on DB
		user.setFoto("https://i.imgur.com/I8MfmC8.png");
		var createdUser = usuarioRepository.save(user);

		// maps the createdUser object to a UserDTO object.
		// It returns the UserDTO object, which represents the newly created user
		return userMapper.toDTO(createdUser);
	}

	public UserLoginDTO authenticateUser(Optional<UsuarioLogin> usuarioLogin) {
		// search for user data
		Optional<Usuario> user = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		// If the user is not found on DB...
		if (user.isEmpty()) {
			// ... throws a custom exception
			throw new UserNotFoundException("O e-mail informado não está cadastrado");
		}

		// create authentication object with username and password provided for login
		var credentials = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(),
				usuarioLogin.get().getSenha());

		try {
			// Authenticate the object
			Authentication authentication = authenticationManager.authenticate(credentials);

			// If authentication is completed
			if (authentication.isAuthenticated()) {

				// Fill the rest of the attributes of the UserLogin
				usuarioLogin.get().setId(user.get().getId());
				usuarioLogin.get().setNomeUsuario(user.get().getNomeUsuario());
				usuarioLogin.get().setFoto(user.get().getFoto());
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
				usuarioLogin.get().setSenha("");

				// returns the filled object and maps to a DTO object
				return userMapper.toLoginDTO(usuarioLogin.get());

			}
		} catch (AuthenticationException e) {
			// If authentication failed
			throw new UserNotFoundException("A autenticação falhou, verifique os dados e tente novamente");
		}

		// If authentication fails without throwing an exception
		throw new UserNotFoundException("Autenticação falhou");

	}

	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}

	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

	public UserDTO updateUser(Usuario usuario, HttpServletRequest request) {
		String userEmail = (String) request.getAttribute("userName");
		Optional<Usuario> requestingUser = usuarioRepository.findByUsuario(userEmail);
		Optional<Usuario> existingUser = usuarioRepository.findById(usuario.getId());

		// check if the user exists
		if (!existingUser.isPresent()) {
			throw new UserNotFoundException("Usuário não encontrado.");
		}

		// check if the ID from user present on the request header
		// is the same ID of the user trying to be updated
		if (existingUser.get().getId() != requestingUser.get().getId()) {
			throw new AccessDeniedException("Você não tem permissão para realizar esta ação.");
		}

		Optional<Usuario> existingUsername = usuarioRepository.findByUsuario(usuario.getUsuario());

		// check if a user with the same username already exists in the database
		// and throws a custom exception if it does
		if ((existingUsername.isPresent()) && (existingUsername.get().getId() != usuario.getId())) {
			throw new UserAlreadyExistsException("O usuário informado já existe");
		}

		// updates user photo if it is null
		if (usuario.getFoto().isBlank()) {
			usuario.setFoto("https://i.imgur.com/I8MfmC8.png");
		}

		// encrypts password before persists object on DB
		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		// update user products
		usuario.setProdutos(existingUser.get().getProdutos());

		// persists update object on DB
		var updatedUser = usuarioRepository.save(usuario);

		// maps to DTO
		return userMapper.toDTO(updatedUser);
	}
}