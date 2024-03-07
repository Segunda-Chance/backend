package com.generation.segundachance.dto;

import java.util.List;

public record UserDTO(String nomeUsuario, String usuario, String photo, List<ProductDTO> produtos) {

	public UserDTO(String nome, String email, String photo) {
        this(nome, email, photo, null);
    }

}
