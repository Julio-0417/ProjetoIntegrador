	package com.cafunematerno.cafunematerno.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tb_grupos")
public class Grupos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGrupo;
	
	@NotBlank
	@Size(min = 5, max = 45, message = "Entre 5 e 45 caracteres")
	private String nomeGrupo;
	
	@NotBlank
	@Size(min = 5, max = 45, message = "Entre 5 e 45 caracteres")
	private String tema;
	
	
	@ManyToMany(mappedBy = "listaGrupos")
	@JsonIgnoreProperties({"postagens", "listaGrupos"})
	public List<Usuarios> listaParticipantes = new ArrayList<>();
	
	@OneToMany(mappedBy = "grupoPertencente", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"grupoPertencente"})
	private List<Postagens> post;
	
	private String foto;
	
	
	public Grupos() {
		
	}

	public List<Postagens> getPost() {
		return post;
	}

	public void setPost(List<Postagens> post) {
		this.post = post;
	}

	public List<Usuarios> getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(List<Usuarios> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}


	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
