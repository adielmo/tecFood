package com.rabelo.tecfood.domain.model.map;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabelo.tecfood.domain.model.Cidade;
import com.rabelo.tecfood.domain.model.request.CidadeRequest;
import com.rabelo.tecfood.domain.model.response.CidadeResponse;

@Component
public class CidadeModelMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public Cidade cidadeRequestToCidade(CidadeRequest cidadeRequest) {
		return modelMapper.map(cidadeRequest, Cidade.class);
	}
	
	
	public CidadeResponse cidadeToCidadeResponse(Cidade cidade) {
		return modelMapper.map(cidade, CidadeResponse.class);
		
	}
	
	public List<CidadeResponse> collectionToCidadeResponse(List<Cidade> cidades){
		return cidades.stream()
				      .map(x -> cidadeToCidadeResponse(x))
				      .collect(Collectors.toList());
	}
	
	public void copyToDomainObject(CidadeRequest cidadeRequest, Cidade cidade) {
		modelMapper.map(cidadeRequest, cidade);
	}
	
	

}
