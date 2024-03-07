package com.generation.segundachance.mapper;

import org.springframework.stereotype.Component;

import com.generation.segundachance.dto.ProductDTO;
import com.generation.segundachance.model.Produto;

@Component
public class ProductMapper {

	public ProductDTO toDTO(Produto produto) {
		return new ProductDTO(
	            produto.getId(),
	            produto.getNomeProduto(),
	            produto.getPreco(),
	            produto.getFoto(),
	            produto.getDescricao(),
	            produto.getCategoria(),
	            produto.getUsuario()
	    );
	}

	public ProductDTO toDTOWithoutUser(Produto produto) {
		return new ProductDTO(
				produto.getId(),
				produto.getNomeProduto(),
				produto.getPreco(),
				produto.getFoto(),
				produto.getDescricao(),
				produto.getCategoria());
	}
	
	public ProductDTO toDTOWithoutCategory(Produto produto) {
		return new ProductDTO(
				produto.getId(),
				produto.getNomeProduto(),
				produto.getPreco(),
				produto.getFoto(),
				produto.getDescricao(),
				produto.getUsuario());
	}

}
