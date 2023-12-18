package com.generation.segundachance.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.segundachance.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	public List <Produto> findAllByNomeProdutoContainingIgnoreCase(@Param("nomeProduto") String nomeProduto);
}
