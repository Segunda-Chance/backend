package com.generation.segundachance.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.generation.segundachance.dto.ProductDTO;
import com.generation.segundachance.dto.ProductWithUserDTO;
import com.generation.segundachance.exception.categoria.CategoryNotFoundException;
import com.generation.segundachance.exception.produto.ProductNotFoundException;
import com.generation.segundachance.exception.usuario.UserNotFoundException;
import com.generation.segundachance.mapper.ProductMapper;
import com.generation.segundachance.mapper.UserMapper;
import com.generation.segundachance.model.Produto;
import com.generation.segundachance.model.Usuario;
import com.generation.segundachance.repository.CategoriaRepository;
import com.generation.segundachance.repository.ProdutoRepository;
import com.generation.segundachance.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProdutoService {

	@Autowired
	UsuarioRepository userRepository;

	@Autowired
	ProdutoRepository productRepository;

	@Autowired
	CategoriaRepository categoryRepository;

	@Autowired
	ProductMapper productMapper;

	@Autowired
	UserMapper userMapper;

	public ProductDTO createProduct(Produto product, HttpServletRequest request) {

		// uses the email found on the request header to search an user on DB
		String userEmail = (String) request.getAttribute("userName");
		Optional<Usuario> user = userRepository.findByUsuario(userEmail);

		// associates the user with the product
		product.setUsuario(user.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado")));

		// checks if the category exists
		if (categoryRepository.existsById(product.getCategoria().getId())) {
			productRepository.save(product);
		} else {
			throw new CategoryNotFoundException("Categoria não encontrada");
		}

		return productMapper.toDTOWithoutUser(product);

	}

	public List<ProductWithUserDTO> getAll() {
		var entityProducts = productRepository.findAll();

		// maps each product on the list to a DTO
		var productDTOS = entityProducts.stream().map(userMapper::toProductWithUserDTO).collect(Collectors.toList());

		return productDTOS;
	}

	public ProductWithUserDTO updateProduct(Produto product, HttpServletRequest request) {

		// return the email sent in the header by JwtAuthFilter configuration
		// "SetAuthentication"
		String userEmail = (String) request.getAttribute("userName");
		Optional<Usuario> requestingUser = userRepository.findByUsuario(userEmail);
		Optional<Produto> productOpt = productRepository.findById(product.getId());

		// checks if product exists
		if (!productOpt.isPresent()) {
			throw new ProductNotFoundException("Produto não encontrado!");
		}

		// checks if the product user_id is different from the request user id
		if (productOpt.get().getUsuario().getId() != requestingUser.get().getId())
			throw new AccessDeniedException("Acesso negado");

		// checks if the category exists
		if (categoryRepository.existsById(product.getCategoria().getId())) {
			product.setUsuario(requestingUser.get());
			var updatedProduct = productRepository.save(product);
			return userMapper.toProductWithUserDTO(updatedProduct);
		}
		throw new CategoryNotFoundException("Categoria não existe!");

	}

	public ProductWithUserDTO getById(Long id) {
		Optional<Produto> productEntity = productRepository.findById(id);
		if (productEntity.isPresent())
			return userMapper.toProductWithUserDTO(productEntity.get());

		throw new ProductNotFoundException("Id " + id + " não encontrado!");
	}

	public List<ProductWithUserDTO> getAllByName(String name) {
		List<Produto> productListEntity = productRepository.findAllByNomeProdutoContainingIgnoreCase(name);
		return productListEntity.stream().map(userMapper::toProductWithUserDTO).collect(Collectors.toList());
	}

	public List<ProductWithUserDTO> priceLessThan(@PathVariable double price) {
		List<Produto> products = productRepository.findByPrecoLessThan(price);
		return products.stream().map(userMapper::toProductWithUserDTO).collect(Collectors.toList());
	}
	
	public List<ProductWithUserDTO> priceGreaterThan(@PathVariable double price) {
		List<Produto> products = productRepository.findByPrecoGreaterThan(price);
		return products.stream().map(userMapper::toProductWithUserDTO).collect(Collectors.toList());
	}
}
