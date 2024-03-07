package com.generation.segundachance.dto;

import java.math.BigDecimal;

import com.generation.segundachance.model.Categoria;

public record ProductWithUserDTO(Long id, String nomeProduto, BigDecimal preco, String foto, String descricao, Categoria categoria, UserDTO usuario) {}
