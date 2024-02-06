package com.generation.segundachance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.generation.segundachance.model.Produto;
import com.generation.segundachance.model.Usuario;
import com.generation.segundachance.repository.ProdutoRepository;
import com.generation.segundachance.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProjectAuthorizationService {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
    private UsuarioRepository userRepository;
	
	@Autowired
    private ProdutoRepository productRepository;
	
	public boolean canDeleteProject(Authentication authentication, Long projectId) {
        
		String userEmail = (String) request.getAttribute("userName");
	    Optional<Usuario> user = userRepository.findByUsuario(userEmail);
	    Optional<Produto> product = productRepository.findById(projectId);
		
        if(user.get().getProdutos().contains(product.get()))
        	return true;
        else
        	return false;
		
    }
	
	public boolean canUpdateProject(Authentication authentication, Long projectId) {
        
		String userEmail = (String) request.getAttribute("userName");
	    Optional<Usuario> user = userRepository.findByUsuario(userEmail);
	    Optional<Produto> product = productRepository.findById(projectId);
		
        if(user.get().getProdutos().contains(product.get()))
        	return true;
        else
        	return false;
		
    }
}
