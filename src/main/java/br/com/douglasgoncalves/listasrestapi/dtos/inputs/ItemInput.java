package br.com.douglasgoncalves.listasrestapi.dtos.inputs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInput {

	@NotBlank(message = "O campo titulo é obrigatório" )
	@Length(message = "O Campo Titulo deve ter no máximo 100 caracteres!", max = 100)
	private String titulo;
	@NotNull
	private Long listaId;
}
