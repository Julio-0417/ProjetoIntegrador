package com.cafunematerno.cafunematerno.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_postagens")
public class Postagens {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPostagem;
	
	@NotBlank
	@Size(min = 5, max = 45, message = "Entre 5 e 45 caracteres")
	private String tituloPostagem;
	
	@NotBlank
	@Size(min = 5, max = 255, message = "Entre 10 e 255 caracteres")
	private String descricaoPostagem;
	
	@Size(min = 5, max = 255, message = "Entre 10 e 255 caracteres")
	private String localizacaoPostagem;
	
	private String urlAnexo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"post"})
	private Grupos grupoPertencente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"postagens", "listaGrupos"})
	private Usuarios usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new java.sql.Date(System.currentTimeMillis());
	
	public Postagens() {
		
	}
	
	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Grupos getGrupoPertencente() {
		return grupoPertencente;
	}

	public void setGrupoPertencente(Grupos grupoPertencente) {
		this.grupoPertencente = grupoPertencente;
	}

	public Long getIdPostagem() {
		return idPostagem;
	}

	public void setIdPostagem(Long idPostagem) {
		this.idPostagem = idPostagem;
	}

	public String getTituloPostagem() {
		return tituloPostagem;
	}

	public void setTituloPostagem(String tituloPostagem) {
		this.tituloPostagem = tituloPostagem;
	}

	public String getDescricaoPostagem() {
		return descricaoPostagem;
	}

	public void setDescricaoPostagem(String descricaoPostagem) {
		this.descricaoPostagem = descricaoPostagem;
	}

	public String getLocalizacaoPostagem() {
		return localizacaoPostagem;
	}

	public void setLocalizacaoPostagem(String localizacaoPostagem) {
		this.localizacaoPostagem = localizacaoPostagem;
	}

	public String getUrlAnexo() {
		return urlAnexo;
	}

	public void setUrlAnexo(String urlAnexo) {
		this.urlAnexo = urlAnexo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
