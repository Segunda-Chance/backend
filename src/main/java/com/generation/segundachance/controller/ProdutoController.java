package com.generation.segundachance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.segundachance.model.Produto;
import com.generation.segundachance.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController{
	@Autowired
	private ProdutoRepository produtoRepository;
	
	//Metodo para realizar o cadastro de um Produto
	@PostMapping
	public ResponseEntity<Produto> post (@Valid @RequestBody Produto produto){
		if(produtoRepository.existsById(produto.getCategoria().getId())) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(produtoRepository.save(produto)); 
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe", null);
	}
	
	// Metodo para listar todos os produtos
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	// Metodo voltado a consulta por ID
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map (reposta -> ResponseEntity.ok(reposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
}
