package com.cafunematerno.cafunematerno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafunematerno.cafunematerno.model.Postagens;
import com.cafunematerno.cafunematerno.repository.PostagensRepository;
import com.cafunematerno.cafunematerno.service.PostagensService;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagensController {
	
	@Autowired
	private PostagensService servicePostagens;
	
	@Autowired
	private  PostagensRepository repositoryPostagens;
	
	@GetMapping
	public ResponseEntity<List<Postagens>> getAll(){
		return servicePostagens.pegarTodasPostagens();
	}
	
	@GetMapping("/id/{id_postagem}")
	public ResponseEntity<Postagens> buscarPostagemPorId(@PathVariable(value = "id_postagem") Long idPostagem) {
		return servicePostagens.procurarIdPostagens(idPostagem);
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagens>> GetByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repositoryPostagens.findAllByTituloPostagemContaining(titulo));
	}
	
	@PostMapping("/salvar/usuario/{id_usuario}")
	public ResponseEntity<Postagens> salvarNovaPostagem(@PathVariable(value = "id_usuario") Long idUsuario, @RequestBody Postagens novaPostagem){
		return servicePostagens.salvarPostagem(idUsuario, novaPostagem);
	}
	
	@PutMapping("/atualizar/{id_postagem}")
	public ResponseEntity<Postagens> alterarPostagem(@PathVariable (value = "id_postagem") Long idPostagem, @RequestBody Postagens postagemAtualizada){
		return servicePostagens.atualizarPostagem(idPostagem, postagemAtualizada);
	}
	
	@DeleteMapping("/deletar")
	public ResponseEntity<Postagens> deletarPostagemAtravesDoId(@RequestParam Long idPostagem) {
		return servicePostagens.deletarIdPostagem(idPostagem);
	}
}
