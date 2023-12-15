package com.generation.segundachance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_categorias")
public class Categoria {
	
	@Id // Primary Key //
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @Column(length = 80)
    @NotBlank(message = "O Atributo nome é obrigatório!")
    @Size(min = 5, max = 80, message="O Atributo nome deve conter no mínimo 5 e no máximo 80 caracteres.")
    private String nome_categoria;

    @Column(length = 50)
    @NotBlank(message = "O Atributo tipo é obrigatório!")
    @Size(min = 10, max = 50, message="O Atributo tipo deve conter no mínimo 10 e no máximo 50 caracteres.")
    private String tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome_categoria() {
		return nome_categoria;
	}

	public void setNome_categoria(String nome_categoria) {
		this.nome_categoria = nome_categoria;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
