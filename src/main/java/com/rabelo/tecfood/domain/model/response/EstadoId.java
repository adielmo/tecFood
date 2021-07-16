package com.rabelo.tecfood.domain.model.response;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoId {
	
	@NotNull
	private Long id;

}
