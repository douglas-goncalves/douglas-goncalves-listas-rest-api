package br.com.douglasgoncalves.listasrestapi.dtos.outputs;

import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemComListaOutput {
	private Long id;
	private String titulo;
	private Boolean concluido;
	private ListaEntity lista;
}
