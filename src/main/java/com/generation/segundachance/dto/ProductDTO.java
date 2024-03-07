package com.generation.segundachance.dto;

import java.math.BigDecimal;

import com.generation.segundachance.model.Categoria;
import com.generation.segundachance.model.Usuario;

public record ProductDTO(Long id, String nomeProduto, BigDecimal preco, String foto, String descricao, Categoria categoria, Usuario usuario) {
	
	// this constructor allows to create an instance of ProductDTO without the 'categoria' or 'usuario' associated
	public ProductDTO(Long id, String nomeProduto, BigDecimal preco, String foto, String descricao) {
        this(id, nomeProduto, preco, foto, descricao, null, null);
    }

	// this constructor allows to create an instance of ProductDTO without the 'usuario' associated
    public ProductDTO(Long id, String nomeProduto, BigDecimal preco, String foto, String descricao, Categoria categoria) {
        this(id, nomeProduto, preco, foto, descricao, categoria, null);
    }

    // this constructor allows to create an instance of ProductDTO without the 'categoria' associated
    public ProductDTO(Long id, String nomeProduto, BigDecimal preco, String foto, String descricao, Usuario usuario) {
        this(id, nomeProduto, preco, foto, descricao, null, usuario);
    }
    
}
