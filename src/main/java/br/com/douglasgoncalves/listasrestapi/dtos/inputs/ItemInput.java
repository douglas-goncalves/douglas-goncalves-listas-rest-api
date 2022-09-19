package br.com.douglasgoncalves.listasrestapi.dtos.inputs;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInput {

	@NotBlank(message = "O campo titulo é obrigatório" )
	@Length(message = "O Campo Titulo deve ter no máximo 100 caracteres!", max = 100)
	String titulo;
	Long lista_id;
}
