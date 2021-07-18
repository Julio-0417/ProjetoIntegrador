package com.cafunematerno.cafunematerno.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.cafunematerno.cafunematerno.model.Postagens;
import com.cafunematerno.cafunematerno.model.Usuarios;
import com.cafunematerno.cafunematerno.repository.PostagensRepository;
import com.cafunematerno.cafunematerno.repository.UsuariosRepository;

@Service
public class PostagensService {
	
	@Autowired
	private PostagensRepository repositoryPostagem;
	
	@Autowired
	private UsuariosRepository repositoryUsuarios;
	
	
	/**
	 * Método utilizado para acessar a lista de postagens no BD.
	 * 
	 * @return ResponseEntity com status No Content caso a lista esteja vazia ou
	 *         ResponseEntity com status Ok, caso existam usuários nesta lista.
	 * @since 1.0
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */
	public ResponseEntity<List<Postagens>> pegarTodasPostagens(){
		List<Postagens> listaDePostagens = repositoryPostagem.findAll();
		if (listaDePostagens.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(listaDePostagens);
		}
	}
	
	/**
	 * Método utilizado para acessar uma postagem no BD a partir de seu Id.
	 * @param id
	 * @return ResponseEntity com status No Content caso não encontre um Id no BD
	 *         esteja vazia ou ResponseEntity com status Ok, caso exista o Id no BD.
	 * @since 1.0
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */
	public ResponseEntity<Postagens> procurarIdPostagens(Long id){
		return repositoryPostagem.findById(id)
				.map(existeIdPostagem -> ResponseEntity.status(200).body(existeIdPostagem))
				.orElse(ResponseEntity.status(204).build());
	}
	
	
	
	/**
	 * Método utilizado para cadastrar uma nova postagem no sistema, validando se
	 * existe um usuário cadastrado no sistema.
	 * 
	 * @param Idusuario
	 * @param novaPostagem
	 * @return ResponseEntity com status Created com a entidade Postagem ou
	 *         ResponseEntity com status Not Acceptable, caso não exista um usuário
	 *         exista.
	 * @since 1.1
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */
	public ResponseEntity<Postagens> salvarPostagem(Long idUsuario, Postagens novaPostagem){
		Optional<Usuarios> verificaUsuario = repositoryUsuarios.findById(idUsuario);
		
		if (verificaUsuario.isPresent()) {
			novaPostagem.setUsuario(verificaUsuario.get());
			return ResponseEntity.status(201).body(repositoryPostagem.save(novaPostagem));
		} else {
			return ResponseEntity.status(406).build();
		}
	}
	
	/**
	 * Método utilizado para verificar se existe o Id da postagem no BD se esse id é existente.
	 * 
	 * @param idPostagem
	 * @param postagemAtualizado
	 * @return ResponseEntity com status Not Modified caso não encontre um Id no BD, 
	 * 	ou ResponseEntity com status Accepted, alterando as informações da postagem selecionado no BD.
	 * @since 1.0
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */
	public ResponseEntity<Postagens> atualizarPostagem(Long idPostagem, Postagens postagemAtualizado){
		Optional<Postagens>  idPostagemJaExiste = repositoryPostagem.findById(idPostagem);
		
		if (idPostagemJaExiste.isPresent()) {
			
			idPostagemJaExiste.get().setTituloPostagem(postagemAtualizado.getTituloPostagem());
			idPostagemJaExiste.get().setDescricaoPostagem(postagemAtualizado.getDescricaoPostagem());
			idPostagemJaExiste.get().setLocalizacaoPostagem(postagemAtualizado.getLocalizacaoPostagem());
			idPostagemJaExiste.get().setUrlAnexo(postagemAtualizado.getUrlAnexo());
			
			return ResponseEntity.status(202).body(repositoryPostagem.save(idPostagemJaExiste.get()));
		} else {
			return ResponseEntity.status(304).build();
		}		
	}
	
	/**
	 * Deleta uma postagem a partir do Id, caso exista.
	 * @param idPostagem
	 * @return Retorna um status 400 caso não exista, ou deleta a postagem caso exista.
	 * @since 2.0
	 * @author Grupo: Angelo, Ellen, Julio, Luciano e Nathalia.
	 */
	public ResponseEntity<Postagens> deletarIdPostagem(Long idPostagem){
		Optional<Postagens> idPostagemExistente = repositoryPostagem.findById(idPostagem);
		
		if (idPostagemExistente.isEmpty()) {
			return ResponseEntity.status(400).build();
		} else {
			repositoryPostagem.deleteById(idPostagem);
			return ResponseEntity.status(200).build();
		}
	}
	
}
