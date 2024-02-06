package com.generation.segundachance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.segundachance.model.Produto;
import com.generation.segundachance.repository.CategoriaRepository;
import com.generation.segundachance.repository.ProdutoRepository;
import com.generation.segundachance.service.ProdutoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController{
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoService productService;
	
	@PostMapping
	public ResponseEntity<?> post (@Valid @RequestBody Produto product, HttpServletRequest request){
				
		if(categoriaRepository.existsById(product.getCategoria().getId())) {
			String userEmail = (String) request.getAttribute("userName");
			return productService.createProduct(product, userEmail); 
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe", null);
	}
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map (reposta -> ResponseEntity.ok(reposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
		@GetMapping("/nomeProduto/{nomeProduto}")
		public ResponseEntity<List<Produto>> getByNomeCategoria(@PathVariable String nomeProduto){
			return ResponseEntity.ok(produtoRepository.findAllByNomeProdutoContainingIgnoreCase(nomeProduto));
		}
		
		@PreAuthorize("@projectAuthorizationService.canUpdateProject(authentication, #id)")
		@PutMapping
		public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
			if(produtoRepository.existsById(produto.getId())) {
				if(categoriaRepository.existsById(produto.getCategoria().getId()))
					return ResponseEntity.status(HttpStatus.OK)
							.body(produtoRepository.save(produto));
				
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		@PreAuthorize("@projectAuthorizationService.canDeleteProject(authentication, #id)")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		@DeleteMapping("/{id}")
		public void delete(@PathVariable Long id) {
			Optional<Produto> produto = produtoRepository.findById(id);
			
			if(produto.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
			produtoRepository.deleteById(id);
		}
		
}






