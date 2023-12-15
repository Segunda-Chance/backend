package com.generation.segundachance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.segundachance.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
List<Categoria> findAllByNomeCategoriaContainingIgnoreCase(@Param("nomeCategoria") String nomeCategoria);
	
	//SELECT * FROM tb_categoria WHERE titulo LIKE "%?%";

}