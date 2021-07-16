package com.rabelo.tecfood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

import com.rabelo.tecfood.domain.model.Estado;
import com.rabelo.tecfood.domain.model.map.EstadoModelMapper;
import com.rabelo.tecfood.domain.model.request.EstadoRequest;
import com.rabelo.tecfood.domain.model.response.EstadoResponse;
import com.rabelo.tecfood.domain.repository.EstadoRepository;
import com.rabelo.tecfood.domain.service.CadastroEstadoService;
import com.rabelo.tecfood.domain.service.exception.EntidadeJaCadastradaException;
import com.rabelo.tecfood.domain.service.exception.EstadoNaoEncontradoException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/estados")
@AllArgsConstructor
public class EstadoController {

	private EstadoRepository estadoRepository;
	private CadastroEstadoService cadastroEstadoService;
	private EstadoModelMapper estadoModelMapper; 

	@GetMapping
	public List<EstadoResponse> lista() {
		return estadoModelMapper
			.collectionToEstadoResponse(estadoRepository.findAll());
	}

	@GetMapping("/{id}")
	public EstadoResponse buscarEstado(@PathVariable Long id) {

	return estadoModelMapper
		.estadoToEstadoResponse(cadastroEstadoService.buscarOuFalhar(id));

		/*
		 * Optional<Estado> estadoId = estadoRepository.findById(id);
		 * 
		 * if (estadoId.isPresent()) { return ResponseEntity.ok(estadoId.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoResponse salvar(@RequestBody @Valid EstadoRequest estadoRequest) {		
	    Estado estado = estadoModelMapper.estadoRequesToEstado(estadoRequest);
		return estadoModelMapper
			.estadoToEstadoResponse(cadastroEstadoService.salvar(estado));
	
		/*
		 * try {
		 * 
		 * Estado estadoSalvo = cadastroEstadoService.salvar(estado); return
		 * ResponseEntity.status(HttpStatus.CREATED).body(estadoSalvo);
		 * 
		 * } catch (EntidadeJaCadastradaException e) {
		 * 
		 * return ResponseEntity.status(HttpStatus.CONFLICT).build(); }
		 */ }

	@PutMapping("/{id}")
	public Estado atualizar(@PathVariable Long id, @RequestBody @Valid EstadoRequest estadoRequest) {

		Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(id);
		//BeanUtils.copyProperties(estado, estadoAtual, "id");
		try {
            estadoModelMapper.copyToDomainObject(estadoRequest, estadoAtual);

			return cadastroEstadoService.atualizar(estadoAtual);

		} catch (EstadoNaoEncontradoException e) {
			throw new EstadoNaoEncontradoException(id);
		}

		/*
		 * Optional<Estado> estadoAtual = estadoRepository.findById(id);
		 * 
		 * if (estadoAtual.isPresent()) { BeanUtils.copyProperties(estado,
		 * estadoAtual.get(), "id"); Estado estadoSalvo =
		 * estadoRepository.save(estadoAtual.get());
		 * 
		 * return ResponseEntity.ok(estadoSalvo); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */ }

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroEstadoService.excluir(id);

		/*
		 * Optional<Estado> estadoId = estadoRepository.findById(id); try { if
		 * (estadoId.isPresent()) { estadoRepository.deleteById(estadoId.get().getId());
		 * return ResponseEntity.noContent().build(); }
		 * 
		 * return ResponseEntity.notFound().build();
		 * 
		 * } catch (DataIntegrityViolationException e) {
		 * 
		 * return ResponseEntity.status(HttpStatus.CONFLICT).build(); }
		 */ }
}
