package com.generation.segundachance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.segundachance.model.Produto;
import com.generation.segundachance.model.Usuario;
import com.generation.segundachance.repository.CategoriaRepository;
import com.generation.segundachance.repository.ProdutoRepository;
import com.generation.segundachance.repository.UsuarioRepository;
import com.generation.segundachance.service.exception.ExcedingProductPropertiesException;
import com.generation.segundachance.service.exception.InvalidProductPropertiesException;
import com.generation.segundachance.service.exception.UserNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired UsuarioRepository userRepository;
	
	@Autowired ProdutoRepository productRepository;
	
	@Autowired CategoriaRepository categoryRepository;
	
	public ResponseEntity<?> createProduct(Produto product, String userEmail){
		
        Optional<Usuario> user = userRepository.findByUsuario(userEmail);
        
        validateProductProperties(product);
        
        product.setUsuario(user.orElseThrow(() -> new UserNotFoundException()));
        
        Produto createdProduct = productRepository.save(product);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto " + createdProduct.getNomeProduto() + " criado com sucesso!");

	}
	
	public ResponseEntity<?>  updateProduct(Produto product, String userEmail){
		
		// checks the existence of product and category
		if(productRepository.existsById(product.getId())) {
			if(categoryRepository.existsById(product.getCategoria().getId())) {
				// validate the fields, sets the user again and update the product
				validateProductProperties(product);
				product.setUsuario(userRepository.findByUsuario(userEmail).get());
				return ResponseEntity.status(HttpStatus.OK)
						.body(productRepository.save(product));	
			}
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
