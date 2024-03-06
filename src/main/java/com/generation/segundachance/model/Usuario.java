package com.generation.segundachance.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

	@Id // Primary Key //
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
	private Long id;

	@Column(length = 30)
	@NotBlank(message = "O Atributo nome é obrigatório!")
	@Size(min = 5, max = 30, message = "O Nome deve ter entre 5 e 30 caracteres.")
	private String nomeUsuario;

	@Column(length = 50)
	@Schema(example = "email@email.com.br")
	@NotBlank(message = "O Atributo usuario é obrigatório!")
	@Size(min = 5, max = 50, message = "O e-mail deve ter entre 5 e 50 caracteres.")
	private String usuario;

	@Column(length = 255)
	@NotBlank(message = "O Atributo senha é obrigatório!")
	@Size(min = 8, max = 255, message = "O Atributo senha deve conter no mínimo 8.")
	private String senha;

	@Column(length = 5000)
	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	private String foto;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Produto> produtos;
	
	public Usuario(Long id, String nomeUsuario, String usuario, String senha, String foto) {
		this.id = id;
		this.nomeUsuario = nomeUsuario;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
	}
	public Usuario() {}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return this.nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}
