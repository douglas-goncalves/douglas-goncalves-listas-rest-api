package br.com.douglasgoncalves.listasrestapi.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.douglasgoncalves.listasrestapi.dtos.inputs.ItemInput;
import br.com.douglasgoncalves.listasrestapi.dtos.outputs.ItemOutput;
import br.com.douglasgoncalves.listasrestapi.entities.ItemEntity;
import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;

@Component
public class ItemConvert {

	@Autowired
	private ModelMapper modelMapper;

	// InputParaEntity
	public ItemEntity inputParaEntity(ItemInput itemInput, ListaEntity lista) {
		ItemEntity itemEntity = modelMapper.map(itemInput, ItemEntity.class);
		itemEntity.setLista(lista);
		return itemEntity;
	}

	// EntityParaOutput
	public ItemOutput entityParaOutput(ItemEntity itemEntity) {
		return modelMapper.map(itemEntity, ItemOutput.class);
	}
	

	// ListaEntityParaListaOutput
	public List<ItemOutput> listaEntityParaListaOutput(List<ItemEntity> listaTodos) {
		List<ItemOutput> itens = listaTodos.stream().map(this::entityParaOutput).collect(Collectors.toList());
		return itens;
	}
	
	// ListaEntityParaListaComListaOutput
	public List<ItemOutput> listaEntityParaListaComListaOutput(List<ItemEntity> listaTodos) {
		List<ItemOutput> itens = listaTodos.stream().map(this::entityParaOutput).collect(Collectors.toList());
		return itens;
	}

	// CopiarInputParaEntity
	public void copiarInputParaEntity(ItemInput itemInput, ListaEntity listaEntity, ItemEntity itemEntity) {
		modelMapper.map(itemInput, itemEntity);
		itemEntity.setLista(listaEntity);
	}

}
