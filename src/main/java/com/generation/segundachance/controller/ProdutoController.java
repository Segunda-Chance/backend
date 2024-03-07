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

import com.generation.segundachance.dto.ProductDTO;
import com.generation.segundachance.dto.ProductWithUserDTO;
import com.generation.segundachance.exception.produto.ProductNotFoundException;
import com.generation.segundachance.model.Produto;
import com.generation.segundachance.repository.ProdutoRepository;
import com.generation.segundachance.service.ProdutoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ProdutoService productService;

	@PostMapping
	public ResponseEntity<ProductDTO> post(@Valid @RequestBody Produto product, HttpServletRequest request) {
		return ResponseEntity.ok(productService.createProduct(product, request));
	}

	@GetMapping
	public ResponseEntity<List<ProductWithUserDTO>> getAll() {
		List<ProductWithUserDTO> productsDTO = productService.getAll();
		return ResponseEntity.ok(productsDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductWithUserDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getById(id));
	}

	@GetMapping("/nomeProduto/{nomeProduto}")
	public ResponseEntity<List<ProductWithUserDTO>> getByNomeCategoria(@PathVariable String nomeProduto) {
		return ResponseEntity.ok(productService.getAllByName(nomeProduto));
	}

	@PutMapping
	public ResponseEntity<ProductWithUserDTO> put(@Valid @RequestBody Produto product, HttpServletRequest request) {
		return ResponseEntity.ok(productService.updateProduct(product, request));
	}

	@PreAuthorize("@productAuthorizationService.canDeleteProduct(authentication, #id)") // uses the method from productAuthorizationService to check if the user is authorized to delete product
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		produtoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/lessthan/{price}")
	public ResponseEntity<List<ProductWithUserDTO>> allLessThan(@PathVariable double price) {
		return ResponseEntity.ok(productService.priceLessThan(price));
	}
	
	@GetMapping("/greaterthan/{price}")
	public ResponseEntity<List<ProductWithUserDTO>> allGreaterThan(@PathVariable double price) {
		return ResponseEntity.ok(productService.priceGreaterThan(price));
	}

}
