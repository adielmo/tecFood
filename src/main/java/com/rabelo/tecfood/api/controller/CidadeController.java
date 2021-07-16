package com.rabelo.tecfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rabelo.tecfood.domain.model.Cidade;
import com.rabelo.tecfood.domain.model.map.CidadeModelMapper;
import com.rabelo.tecfood.domain.model.request.CidadeRequest;
import com.rabelo.tecfood.domain.model.response.CidadeResponse;
import com.rabelo.tecfood.domain.repository.CidadeRepository;
import com.rabelo.tecfood.domain.service.CadastroCidadeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cidades")
@AllArgsConstructor
public class CidadeController {

	private CidadeRepository cidadeRepository;
	private CadastroCidadeService cadastroCidadeService;
	private CidadeModelMapper cidadeModel;

	@GetMapping
	public List<CidadeResponse> listar() {
		return cidadeModel.collectionToCidadeResponse(cidadeRepository.findAll());
	}

	@GetMapping("/{id}")
	public CidadeResponse buscarCidade(@PathVariable Long id) {
		
		return cidadeModel.cidadeToCidadeResponse(cadastroCidadeService.buscarOuFalhar(id));
		/*
		 * Cidade cidadeAtual = cidadeRepository.findById(id).orElse(null); return
		 * cidadeAtual != null ? ResponseEntity.ok(cidadeAtual) :
		 * ResponseEntity.notFound().build();
		 */
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeResponse salvar(@RequestBody @Valid CidadeRequest cidadeRequest) {
		 Cidade cidade = cidadeModel.cidadeRequestToCidade(cidadeRequest);
		return cidadeModel.cidadeToCidadeResponse(cadastroCidadeService.salvar(cidade));

		/*
		 * try { Cidade cidadeAtual = cadastroCidadeService.salvar(cidade); return
		 * ResponseEntity.status(HttpStatus.CREATED).body(cidadeAtual);
		 * 
		 * } catch (EntidadeNaoEncontradaException e) { return
		 * ResponseEntity.notFound().build(); }
		 */	}

	@PutMapping("/{id}")
	public CidadeResponse atualizar(@PathVariable Long id, @RequestBody @Valid CidadeRequest cidadeRequest) {
		
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(id);
		cidadeModel.copyToDomainObject(cidadeRequest, cidade);	
		
		return cidadeModel.cidadeToCidadeResponse(cadastroCidadeService.atualizar(id, cidade));
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCidadeService.excluir(id);
		/*
		 * Cidade cidade = cidadeRepository.findById(id).orElse(null); try { if (cidade
		 * != null) { cidadeRepository.delete(cidade); return
		 * ResponseEntity.noContent().build(); } return
		 * ResponseEntity.notFound().build();
		 * 
		 * } catch (EntidadeEmUsoException e) { return
		 * ResponseEntity.status(HttpStatus.CONFLICT).build(); }
		 */	}
	


}
