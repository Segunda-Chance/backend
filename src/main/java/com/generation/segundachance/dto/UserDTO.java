package com.generation.segundachance.dto;

import java.util.List;

import com.generation.segundachance.model.Produto;

public record UserDTO(String nomeUsuario, String usuario, String photo, List<Produto> produtos) {
	public UserDTO(String nome, String email, String photo) {
        this(nome, email, photo, null);
    }
}
