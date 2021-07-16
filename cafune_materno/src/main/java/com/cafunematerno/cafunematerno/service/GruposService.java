package com.cafunematerno.cafunematerno.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cafunematerno.cafunematerno.model.Grupos;
import com.cafunematerno.cafunematerno.model.Usuarios;
import com.cafunematerno.cafunematerno.repository.GruposRepository;
import com.cafunematerno.cafunematerno.repository.UsuariosRepository;

@Service
public class GruposService {

	@Autowired
	private GruposRepository repositoryGrupos;

	@Autowired
	private UsuariosRepository repositoryUsuarios;
	
	/**
	 * Método utilizado para acessar a lista de grupos no BD.
	 * 
	 * @return ResponseEntity com status No Content caso a lista esteja vazia ou
	 * 			ResponseEntity com status Ok, caso existam grupos nesta lista.
	 * @since 1.0
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */

	public ResponseEntity<List<Grupos>> pegarTodosGrupos() {
		List<Grupos> listaDeGrupos = repositoryGrupos.findAll();
		if (listaDeGrupos.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(listaDeGrupos);
		}
	}
	
	/**
	 * Método utilizado para acessar um grupo no BD a partir de seu Id.
	 * 
	 * @param id
	 * @return ResponseEntity com status Ok, caso exista o Id no BD,
	 * 			e ResponseEntity com status No Content caso não encontre 
	 * 			um Id no BD e esteja vazia.
	 * @since 1.0
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */

	public ResponseEntity<Grupos> procurarIdGrupos(Long id) {
		return repositoryGrupos.findById(id).map(existeIdGrupos -> ResponseEntity.status(200).body(existeIdGrupos))
				.orElse(ResponseEntity.status(204).build());
	}
	
	/**
	 * Método utilizado para cadastrar um novo grupo no sistema, validando se
	 * existe um grupo cadastrado no sistema.
	 * 
	 * @param idUsuario
	 * @param novoGrupo
	 * @return ResponseEntity com status Created com a entidade Grupo ou
	 * 			ResponseEntity com status Not Acceptable, caso não exista um grupo
	 *         	exista.
	 * @since 1.1
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */

	public ResponseEntity<Usuarios> salvarGrupos(Long idUsuario, Grupos novoGrupo) {
		Optional<Usuarios> verificaUsuario = repositoryUsuarios.findById(idUsuario);
		Optional<Object> verificaGrupo = repositoryGrupos.findByNomeGrupo(novoGrupo.getNomeGrupo());
		
		if (verificaUsuario.isPresent() && verificaGrupo.isEmpty()) {
			
			novoGrupo.setNomeGrupo(novoGrupo.getNomeGrupo());
			novoGrupo.setTema(novoGrupo.getTema());
			Grupos grupoCriado = repositoryGrupos.save(novoGrupo);
			verificaUsuario.get().getListaGrupos().add(grupoCriado);
			return ResponseEntity.status(201).body(repositoryUsuarios.save(verificaUsuario.get()));
		} else {
			return ResponseEntity.status(406).build();
		}	
	}
	
	/**
	 * Adicionar usuario em um grupo.
	 * @param idUsuario
	 * @param idGrupo
	 * @return Response entity com status 201 se adicionado na lista.
	 * @since 1.0
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */
	public ResponseEntity<Object> adicionarUsuarioGrupo(Long idUsuario, Long idGrupo) {
		Optional<Usuarios> verificaUsuario = repositoryUsuarios.findById(idUsuario);
		Optional<Grupos> verificaGrupo = repositoryGrupos.findById(idGrupo);
		
		if (verificaUsuario.isPresent() && verificaGrupo.isPresent() && !verificaGrupo.get().getListaParticipantes().contains(verificaUsuario.get())) {
			verificaUsuario.get().getListaGrupos().add(verificaGrupo.get());
			return ResponseEntity.status(201).body(repositoryUsuarios.save(verificaUsuario.get()));
		} else {
			return ResponseEntity.status(406).build();
		}	
	}
	
	/**
	 * Remove o usuario da lista de grupos.
	 * @param idUsuario
	 * @param idGrupo
	 * @return Response Entity com stauts 201 se removido do grupo. 
	 * @since 1.0
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */
	public ResponseEntity<Object> removerUsuarioGrupo(Long idUsuario, Long idGrupo) {
		Optional<Usuarios> verificaUsuario = repositoryUsuarios.findById(idUsuario);
		Optional<Grupos> verificaGrupo = repositoryGrupos.findById(idGrupo);
		
		if (verificaUsuario.isPresent() && verificaGrupo.isPresent() && verificaGrupo.get().getListaParticipantes().contains(verificaUsuario.get())) {
			verificaUsuario.get().getListaGrupos().remove(verificaGrupo.get());
			return ResponseEntity.status(201).body(repositoryUsuarios.save(verificaUsuario.get()));
		} else {
			return ResponseEntity.status(406).build();
		}	
	}
	
	
	/**
	 * Método utilizado para verificar se existe o Id do grupo no BD se esse id é existente.
	 * 
	 * @param idGrupo
	 * @param grupoAtualizado
	 * @return ResponseEntity com status Not Modified caso não encontre um Id no BD,
	 * 			ou ResponseEntity com status Accepted, alterando as informações do grupo
	 * 			selecionado no BD.
	 * @since 1.0
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */

	public ResponseEntity<Grupos> atualizarGrupo(Long idGrupo, Grupos grupoAtualizado) {
		Optional<Grupos> idGrupoJaExiste = repositoryGrupos.findById(idGrupo);

		if (idGrupoJaExiste.isPresent()) {
			idGrupoJaExiste.get().setNomeGrupo(grupoAtualizado.getNomeGrupo());
			idGrupoJaExiste.get().setTema(grupoAtualizado.getTema());
			idGrupoJaExiste.get().setFoto(grupoAtualizado.getFoto());

			return ResponseEntity.status(202).body(repositoryGrupos.save(idGrupoJaExiste.get()));
		} else {
			return ResponseEntity.status(304).build();
		}
	}
	
	/**
	 * Deleta um grupo a partir do Id, caso exista.
	 * @param idGrupo
	 * @return Retorna um status 400 caso não exista, ou deleta o grupo caso exista.
	 * @since 2.0
	 * @author Grupo:Angelo, Ellen, Julio, Luciano e Nathalia.
	 */

	public ResponseEntity<Grupos> deletarIdGrupo(Long idGrupo) {
		Optional<Grupos> idGrupoExistente = repositoryGrupos.findById(idGrupo);

		if (idGrupoExistente.isEmpty()) {
			return ResponseEntity.status(400).build();
		} else {
			repositoryGrupos.deleteById(idGrupo);
			return ResponseEntity.status(200).build();
		}
	}
}
