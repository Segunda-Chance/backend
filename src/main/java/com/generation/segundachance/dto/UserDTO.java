package com.generation.segundachance.dto;

import java.util.List;

import com.generation.segundachance.model.Produto;

public record UserDTO(String nomeUsuario, String usuario, List<Produto> produtos) {
	public UserDTO(String nome, String email) {
        this(nome, email, null); // Valor padrão para telefone é null
    }
}
