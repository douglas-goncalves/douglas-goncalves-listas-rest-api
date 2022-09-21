package br.com.douglasgoncalves.listasrestapi.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.douglasgoncalves.listasrestapi.configs.ControllerConfig;
import br.com.douglasgoncalves.listasrestapi.converts.ItemConvert;
import br.com.douglasgoncalves.listasrestapi.converts.ListaConvert;
import br.com.douglasgoncalves.listasrestapi.dtos.inputs.ItemInput;
import br.com.douglasgoncalves.listasrestapi.dtos.inputs.ListaInput;
import br.com.douglasgoncalves.listasrestapi.dtos.outputs.ItemOutput;
import br.com.douglasgoncalves.listasrestapi.dtos.outputs.ListaOutput;
import br.com.douglasgoncalves.listasrestapi.entities.ItemEntity;
import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;
import br.com.douglasgoncalves.listasrestapi.services.ItemService;
import br.com.douglasgoncalves.listasrestapi.services.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lista:")
@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/listas")
@CrossOrigin(origins = "*")
public class ListaController {

	@Autowired
	private ListaConvert listaConvert;

	@Autowired
	private ListaService listaService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemConvert itemConvert;

	//(C) Cadastra
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Cadastar uma nova Lista", description = "EndPoint usado para Cadastrar uma nova Lista")
	public ResponseEntity<ListaOutput> cadastra(
			@Parameter(description = "Representação da Lista") @Valid @RequestBody ListaInput listaInput,
			UriComponentsBuilder uriBuild) {
		ListaEntity listaNovo = listaConvert.inputParaEntity(listaInput);
		ListaEntity listaSalvo = listaService.cadastrar(listaNovo);
		URI uri = uriBuild.path(ControllerConfig.PRE_URL + "/listas").buildAndExpand(listaSalvo.getId()).toUri();

		return ResponseEntity.created(uri).body(listaConvert.entityParaOutput(listaSalvo));
	}
	
	//(D) Atualiza 
	@PutMapping("/{id}")
	@Operation(summary = "Atualiza Lista com os novos dados", description = "EndPoint usado para Atualizar a Lista com base nos dados passados")
	public ListaOutput atualiza(@Parameter(description = "Id da Lista", example = "1") @PathVariable Long id,
			@RequestBody ListaInput listaInput) {
		ListaEntity listaEntity = listaService.buscaPeloId(id);
		listaConvert.copiarInputParaEntity(listaInput, listaEntity);
		return listaConvert.entityParaOutput(listaService.atualiza(listaEntity));
	}

	//(E) Lista Todos 
	@GetMapping
	@Operation(summary = "Lista Todas as Lista", description = "EndPoint usado para Listar todas as Listas")
	public List<ListaOutput> ListaTodos() {
		return listaConvert.listaEntityParaListaOutput(listaService.listaTodos());
	}

	//(F) Busca Pelo id 
	@Operation(summary = "Busca Lista pelo Id", description = "EndPoint usado para fazer a busca de uma Lista pelo Id")
	@GetMapping("/{id}")
	public ListaOutput BuscaPeloId(@Parameter(description = "Id da Lista", example = "1") @PathVariable Long id) {
		return listaConvert.entityParaOutput(listaService.buscaPeloId(id));
	}

	//(G) Deleta 
	@Operation(summary = "Deleta Lista pelo Id", description = "EndPoint usado para deletar Uma Lista pelo Id")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletaPeloId(@Parameter(description = "Id da Lista", example = "1") @PathVariable Long id) {
		ListaEntity lista = listaService.buscaPeloId(id);
		itemService.deletaTodosItensDaLista(lista);
		listaService.deletaPeloId(id);
	}


	//(L) Marcar Item 
	@PutMapping("/{listaId}/marcar-item-como-concluido/{itenId}")
	@Operation(summary = "Marca Item da Lista como concluido", description = "EndPoint usado para marcar um item como concluido")
	public ItemOutput marcarItem(@Parameter(description = "Id da Lista", example = "1") @PathVariable Long listaId,
			@Parameter(description = "Id do Item", example = "1") @PathVariable Long itenId) {
		ItemEntity item = itemService.buscaItemPeloListaIdItemId(listaId, itenId);
		itemService.marcarComoConcluido(item);

		return itemConvert.entityParaOutput(item);
	}

	//(M) Desmarcar Item 
	@PutMapping("/{listaId}/desmarcar-item-concluido/{itenId}")
	@Operation(summary = "Desmarca Item concluido da Lista", description = "EndPoint usado para desmarcar um item concluido")
	public ItemOutput desmarcarItem(@Parameter(description = "Id da Lista", example = "1") @PathVariable Long listaId,
			@Parameter(description = "Id do Item", example = "1") @PathVariable Long itenId) {
		ItemEntity item = itemService.buscaItemPeloListaIdItemId(listaId, itenId);
		itemService.desmarcarConcluido(item);

		return itemConvert.entityParaOutput(item);
	}
	
	//(N) Lista Todos os itens da Lista 
	@GetMapping("{id}/itens")
	@Operation(summary = "Lista todos os Itens de uma lista", description = "EndPoint usado para Listar todos os Itens de uma lista")
	public List<ItemOutput> ListaItensDaLista(@Parameter(description = "Id da Lista", example = "1") @PathVariable Long id) {
		ListaEntity lista = listaService.buscaPeloId(id);
		List<ItemEntity> itens = itemService.listaItensPelaLista(lista);

		return itemConvert.listaEntityParaListaOutput(itens);
	}


}
