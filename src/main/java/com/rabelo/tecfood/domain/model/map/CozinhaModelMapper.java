package com.rabelo.tecfood.domain.model.map;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabelo.tecfood.domain.model.Cozinha;
import com.rabelo.tecfood.domain.model.request.CozinhaRequest;
import com.rabelo.tecfood.domain.model.response.CozinhaResponse;

@Component
public class CozinhaModelMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public Cozinha cozinhaResquestToCozinha(CozinhaRequest cozinhaRequest) {
		return modelMapper.map(cozinhaRequest, Cozinha.class);
	}
	
	public CozinhaResponse cozinhaToCozinhaResponse(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaResponse.class);
	}
	
	
	public List<CozinhaResponse> collectionToCozinhaResponse(List<Cozinha> cozinhas){
	return cozinhas.stream()
			       .map(x -> cozinhaToCozinhaResponse(x))
			       .collect(Collectors.toList());
	}
	
	public void copyToDomainObject(CozinhaRequest cozinhaRequest, Cozinha cozinha) {
		modelMapper.map(cozinhaRequest, cozinha);
	}

}
