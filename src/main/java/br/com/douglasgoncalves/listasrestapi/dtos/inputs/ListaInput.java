package br.com.douglasgoncalves.listasrestapi.dtos.inputs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaInput {
	@NotBlank(message = "O campo Titulo é obrigatório!")
	@Length(message = "O campo Titulo deve ter no máximo 100 caracteres!", max = 100)
	private String titulo;
}
