package com.generation.segundachance.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.generation.segundachance.dto.ProductDTO;
import com.generation.segundachance.dto.ProductWithUserDTO;
import com.generation.segundachance.dto.UserDTO;
import com.generation.segundachance.dto.UserLoginDTO;
import com.generation.segundachance.model.Produto;
import com.generation.segundachance.model.Usuario;
import com.generation.segundachance.model.UsuarioLogin;

@Component
public class UserMapper {
	
	@Autowired ProductMapper productMapper;
	
	public UserDTO toDTO(Usuario user) {
		if (user.getProdutos() == null || user.getProdutos().isEmpty()) {
	        user.setProdutos(new ArrayList<>());
	    }
		
		List<ProductDTO> productDTOs = user.getProdutos().stream()
	            .map(produto -> new ProductDTO(
	                    produto.getId(),
	                    produto.getNomeProduto(),
	                    produto.getPreco(),
	                    produto.getFoto(),
	                    produto.getDescricao(),
	                    produto.getCategoria()))
	            .collect(Collectors.toList());
		
		var userDTO = new UserDTO(
				user.getNomeUsuario(), 
				user.getUsuario(),
				user.getFoto(),
				productDTOs);
				
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
	
	public UserDTO toDTOWithourProducts(Usuario user) {
        return new UserDTO(
                user.getNomeUsuario(),
                user.getUsuario(),
                user.getFoto()
        );
    }
	
	public ProductWithUserDTO toProductWithUserDTO(Produto produto) {
        UserDTO userDTO = toDTOWithourProducts(produto.getUsuario());

        return new ProductWithUserDTO(
                produto.getId(),
                produto.getNomeProduto(),
                produto.getPreco(),
                produto.getFoto(),
                produto.getDescricao(),
                produto.getCategoria(),
                userDTO
        );
    }
}
