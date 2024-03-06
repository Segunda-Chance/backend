package com.generation.segundachance.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.generation.segundachance.dto.UserDTO;
import com.generation.segundachance.dto.UserLoginDTO;
import com.generation.segundachance.model.Usuario;
import com.generation.segundachance.model.UsuarioLogin;

@Component
public class UserMapper {
	public UserDTO toDTO(Usuario user) {
		if (user.getProdutos() == null || user.getProdutos().isEmpty()) {
	        user.setProdutos(new ArrayList<>());
	    }
		var userDTO = new UserDTO(
				user.getNomeUsuario(), 
				user.getUsuario(),
				user.getFoto(),
				user.getProdutos());
				
		return userDTO;
	}
	
	public UserLoginDTO toLoginDTO(UsuarioLogin userLogin) {
		var userLoginDTO = new UserLoginDTO(
				userLogin.getNomeUsuario(), 
				userLogin.getUsuario(),
				userLogin.getToken(),
				userLogin.getFoto());
		
		return userLoginDTO;
	}
}
