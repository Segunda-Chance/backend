package com.generation.segundachance.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.generation.segundachance.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	List<Produto> findAllByNome_produtoContainingIgnoreCase(@Param("nome_produto") String nome_produto);
	List<Produto> findAllByPrecoContaining(BigDecimal preco);
}
