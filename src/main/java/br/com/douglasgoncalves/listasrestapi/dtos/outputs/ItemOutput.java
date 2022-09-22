package br.com.douglasgoncalves.listasrestapi.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOutput {
	private Long id;
	private String titulo;
	private Boolean concluido;
	private ListaOutput lista;
}
