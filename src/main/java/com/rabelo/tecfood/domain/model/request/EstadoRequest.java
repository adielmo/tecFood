package com.rabelo.tecfood.domain.model.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoRequest {
	
	
	@NotNull
	private String nome;

}
