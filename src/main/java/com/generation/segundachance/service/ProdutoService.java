package com.generation.segundachance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.generation.segundachance.model.Produto;
import com.generation.segundachance.model.Usuario;
import com.generation.segundachance.repository.ProdutoRepository;
import com.generation.segundachance.repository.UsuarioRepository;
import com.generation.segundachance.service.exception.ExcedingProductPropertiesException;
import com.generation.segundachance.service.exception.InvalidProductPropertiesException;
import com.generation.segundachance.service.exception.UserNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired UsuarioRepository usuarioRepository;
	
	@Autowired ProdutoRepository produtoRepository;
	
	public ResponseEntity<?> createProduct(Produto product, String userEmail){
		
        Optional<Usuario> user = usuarioRepository.findByUsuario(userEmail);
        
        // TODO: validateProductProperties(product) - exceptions to validate fields
        
        product.setUsuario(user.orElseThrow(() -> new UserNotFoundException()));
        
        Produto createdProduct = produtoRepository.save(product);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto " + createdProduct.getNomeProduto() + " criado com sucesso!");

	}
	
	public void validateProductProperties(Produto product) {
		ExcedingProductPropertiesException.ThrowIfDataIsTooLong("nomeProduto", product.getNomeProduto(), 50);
		ExcedingProductPropertiesException.ThrowIfDataIsTooLong("preco", product.getPreco().toString(), 10);
		ExcedingProductPropertiesException.ThrowIfDataIsTooLong("foto", product.getFoto(), 300);
		ExcedingProductPropertiesException.ThrowIfDataIsTooLong("descrição", product.getDescricao(), 500);
		InvalidProductPropertiesException.ThrowIfIsEmpty("nomeProduto", product.getNomeProduto());
		InvalidProductPropertiesException.ThrowIfIsEmpty("preco", product.getPreco().toString());
		InvalidProductPropertiesException.ThrowIfIsEmpty("foto", product.getFoto());
		InvalidProductPropertiesException.ThrowIfIsEmpty("descrição", product.getDescricao());
	}
}
