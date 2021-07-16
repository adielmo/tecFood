package com.rabelo.tecfood.domain.model.map;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabelo.tecfood.domain.model.Estado;
import com.rabelo.tecfood.domain.model.request.EstadoRequest;
import com.rabelo.tecfood.domain.model.response.EstadoResponse;

@Component
public class EstadoModelMapper {
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado estadoRequesToEstado(EstadoRequest estadoRequest) {
		return modelMapper.map(estadoRequest, Estado.class);
	}
	
	public EstadoResponse estadoToEstadoResponse(Estado estado) {
		return modelMapper.map(estado, EstadoResponse.class);
	}
	
	public List<EstadoResponse> collectionToEstadoResponse(List<Estado> estados){
		return estados.stream()
				      .map(x -> estadoToEstadoResponse(x))
				      .collect(Collectors.toList());
	}
	
	public void copyToDomainObject(EstadoRequest estadoRequest, Estado estado) {
		modelMapper.map(estadoRequest, estado);
	}
	

}
