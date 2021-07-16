package com.rabelo.tecfood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.rabelo.tecfood.domain.model.Cozinha;
import com.rabelo.tecfood.domain.model.map.CozinhaModelMapper;
import com.rabelo.tecfood.domain.model.request.CozinhaRequest;
import com.rabelo.tecfood.domain.model.response.CozinhaResponse;
import com.rabelo.tecfood.domain.repository.CozinhaRepository;
import com.rabelo.tecfood.domain.service.CadastroCozinhaService;
import com.rabelo.tecfood.domain.service.exception.EntidadeJaCadastradaException;
import com.rabelo.tecfood.domain.service.exception.EntidadeNaoEncontradaException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/cozinhas")
//,produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
public class CozinhaController {

	
	private CozinhaRepository cozinhaRepository;	
	private CadastroCozinhaService cadastrocozinhaService;
	private CozinhaModelMapper cozinhaModel; 

	@GetMapping()
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	@GetMapping("/{id}")
	public CozinhaResponse buscarCozinha(@PathVariable Long id) {
		
		return cozinhaModel.cozinhaToCozinhaResponse(cadastrocozinhaService.buscarOuFalhar(id));
		
		/*
		 * Cozinha cozinha = cozinhaRepository.findById(id).orElse(null);
		 * 
		 * if (cozinha != null) {
		 * 
		 * return ResponseEntity.ok(cozinha); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaResponse salvar(@RequestBody @Valid CozinhaRequest cozinhaRequest) {
		Cozinha cozinha = cozinhaModel.cozinhaResquestToCozinha(cozinhaRequest);
		return cozinhaModel.cozinhaToCozinhaResponse(cadastrocozinhaService.salva(cozinha));

		/*
		 * try { Cozinha cozinhasalva = cadastrocozinhaService.salva(cozinha);
		 * 
		 * return ResponseEntity.status(HttpStatus.CREATED).body(cozinhasalva);
		 * 
		 * } catch (EntidadeJaCadastradaException e) {
		 * 
		 * return ResponseEntity.badRequest().body(e.getLocalizedMessage()); }
		 */	}

	@PutMapping("/{id}")
	public CozinhaResponse atualizar(@PathVariable Long id, @RequestBody CozinhaRequest cozinhaRequest) {		
		
		Cozinha cozinhaDb = cadastrocozinhaService.buscarOuFalhar(id);
		cozinhaModel.copyToDomainObject(cozinhaRequest, cozinhaDb);
		//BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		
		return cozinhaModel.cozinhaToCozinhaResponse(cozinhaRepository.save(cozinhaDb));

		/*
		 * Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);
		 * 
		 * if (cozinhaAtual.isPresent()) {
		 * 
		 * BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id"); Cozinha
		 * cozinhaSalva = cozinhaRepository.save(cozinhaAtual.get());
		 * 
		 * return ResponseEntity.ok(cozinhaSalva); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */
	}

	/*
	 * @DeleteMapping("/{id}") public ResponseEntity<Cozinha> remover(@PathVariable
	 * Long id) { Optional<Cozinha> cozinhaId = cozinhaRepository.findById(id);
	 * 
	 * try { if (cozinhaId.get() != null) {
	 * cozinhaRepository.deleteById(cozinhaId.get().getId());
	 * 
	 * return ResponseEntity.noContent().build(); }
	 * 
	 * return ResponseEntity.notFound().build();
	 * 
	 * } catch (DataIntegrityViolationException e) {
	 * 
	 * return ResponseEntity.status(HttpStatus.CONFLICT).build(); } }
	 */
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		
	cadastrocozinhaService.excluir(id);
	}

}
