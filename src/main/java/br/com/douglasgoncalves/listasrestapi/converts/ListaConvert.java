package br.com.douglasgoncalves.listasrestapi.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.douglasgoncalves.listasrestapi.dtos.inputs.ListaInput;
import br.com.douglasgoncalves.listasrestapi.dtos.outputs.ListaOutput;
import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;

@Component
public class ListaConvert {

	@Autowired
	private ModelMapper modelMapper;

	// InputParaEntity
	public ListaEntity inputParaEntity(ListaInput listaInput) {
		ListaEntity listaEntity = modelMapper.map(listaInput, ListaEntity.class);
		return listaEntity;
	}

	// EntityParaOutput
	public ListaOutput entityParaOutput(ListaEntity listaSalvo) {
		return modelMapper.map(listaSalvo, ListaOutput.class);
	}

	// ListEntityParaListOutput
	public List<ListaOutput> listaEntityParaListaOutput(List<ListaEntity> listaTodos) {
		List<ListaOutput> itens = listaTodos.stream().map(this::entityParaOutput).collect(Collectors.toList());
		return itens;
	}

	// CopiarInputParaEntity
	public void copiarInputParaEntity(ListaInput listaInput, ListaEntity listaEntity) {
		modelMapper.map(listaInput, listaEntity);
	}

}
