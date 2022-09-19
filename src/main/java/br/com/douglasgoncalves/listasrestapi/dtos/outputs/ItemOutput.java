package br.com.douglasgoncalves.listasrestapi.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOutput {
	Long id;
	String titulo;
	Boolean concluido;
}
