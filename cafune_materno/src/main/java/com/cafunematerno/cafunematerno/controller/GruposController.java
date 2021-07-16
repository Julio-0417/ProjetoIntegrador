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

import com.cafunematerno.cafunematerno.model.Grupos;
import com.cafunematerno.cafunematerno.model.Usuarios;
import com.cafunematerno.cafunematerno.repository.GruposRepository;
import com.cafunematerno.cafunematerno.service.GruposService;

@RestController
@RequestMapping("/grupos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GruposController {
	
	@Autowired
	private GruposService serviceGrupos;
	
	@Autowired
	private GruposRepository gruposRepository;
	

	@GetMapping
	public ResponseEntity<List<Grupos>> getAll(){
		return serviceGrupos.pegarTodosGrupos();
	}
	
	@GetMapping("/id/{id_grupo}")
	public ResponseEntity<Grupos> buscarGrupoPorId(@PathVariable(value = "id_grupo") Long idGrupo) {
		return serviceGrupos.procurarIdGrupos(idGrupo);
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Grupos>> getByName(@PathVariable String nome){
		return ResponseEntity.ok(gruposRepository.findAllByNomeGrupoContaining(nome));
	}
	
	@PostMapping("/salvar/usuario/{id_usuario}")
	public ResponseEntity<Usuarios> salvarNovoGrupo(@PathVariable(value = "id_usuario") Long idUsuario, @RequestBody Grupos novoGrupo) {
		return serviceGrupos.salvarGrupos(idUsuario, novoGrupo);
	}
	
	@PutMapping("/id/add/grupo/{id_grupo}/usuario/{id_usuario}")
	public ResponseEntity<Object> adicionarUsuario(@PathVariable(value = "id_usuario") Long idUsuario, @PathVariable(value = "id_grupo") Long idGrupo) {
		return serviceGrupos.adicionarUsuarioGrupo(idUsuario, idGrupo);
	}
	
	@PutMapping("/id/remove/grupo/{id_grupo}/usuario/{id_usuario}")
	public ResponseEntity<Object> removeUsuario(@PathVariable(value = "id_usuario") Long idUsuario, @PathVariable(value = "id_grupo") Long idGrupo) {
		return serviceGrupos.removerUsuarioGrupo(idUsuario, idGrupo);
	}
	
	@PutMapping("/atualizar/{id_grupo}")
	public ResponseEntity<Grupos> alterarGrupo(@PathVariable (value = "id_grupo") Long idGrupo, @RequestBody Grupos grupoAtualizado) {
		return serviceGrupos.atualizarGrupo(idGrupo, grupoAtualizado);
	}
	
	@DeleteMapping("/deletar")
	public ResponseEntity<Grupos> deletarGrupoAtravesDoId(@RequestParam Long idGrupo) {
		return serviceGrupos.deletarIdGrupo(idGrupo);
	}
}
