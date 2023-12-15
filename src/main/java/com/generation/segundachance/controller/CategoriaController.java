package com.generation.segundachance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.segundachance.model.Categoria;
import com.generation.segundachance.repository.CategoriaRepository;


@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	
	@GetMapping("/nomeCategoria/{nomeCategoria}")
	public ResponseEntity<List<Categoria>> getByNome_categoria(@PathVariable String nomeCategoria){
		return ResponseEntity.ok(categoriaRepository.findAllByNomeCategoriaContainingIgnoreCase(nomeCategoria));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		
		Optional<Categoria> categoria = categoriaRepository.findById(id);

		if (categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		categoriaRepository.deleteById(id);
		
		// DELETE FROM tb_categoria WHERE id = ?;
	}
}
