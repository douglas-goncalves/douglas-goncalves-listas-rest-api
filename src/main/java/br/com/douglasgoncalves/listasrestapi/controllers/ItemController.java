package br.com.douglasgoncalves.listasrestapi.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import br.com.douglasgoncalves.listasrestapi.dtos.inputs.ItemInput;
import br.com.douglasgoncalves.listasrestapi.dtos.outputs.ItemComListaOutput;
import br.com.douglasgoncalves.listasrestapi.dtos.outputs.ItemOutput;
import br.com.douglasgoncalves.listasrestapi.entities.ItemEntity;
import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;
import br.com.douglasgoncalves.listasrestapi.services.ItemService;
import br.com.douglasgoncalves.listasrestapi.services.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Item:")
@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/itens")
@CrossOrigin(origins = "*")
public class ItemController {

	@Autowired
	private ItemConvert itemConvert;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ListaService listaService;

	//(I) Cadastra 
	@Operation(summary = "Cadastar um novo Item", description = "EndPoint usado para Cadastrar um novo Item")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ItemComListaOutput> cadastra(
		@Parameter(description = "Representação do Item")	@Valid @RequestBody ItemInput itemInput, UriComponentsBuilder uriBuild) {
		ListaEntity lista = listaService.buscaPeloId(itemInput.getListaId());
		ItemEntity itemNovo = itemConvert.inputParaEntity(itemInput, lista);
		
		ItemEntity itemSalvo = itemService.cadastra(itemNovo);
		URI uri = uriBuild.path(ControllerConfig.PRE_URL + "/itens").buildAndExpand(itemSalvo.getId()).toUri();

		return ResponseEntity.created(uri).body(itemConvert.entityParaComListaOutput(itemSalvo));
	}

	//(J) atualiza 
	@PutMapping("/{id}")
	@Operation(summary = "Atualiza Item com os novos dados", description = "EndPoint usado para Atualizar o Item com os dados passados")
	public ItemComListaOutput atualiza(@Parameter(description = "Id do Item", example = "1") @PathVariable Long id,
			@RequestBody ItemInput itemInput) {
		ItemEntity itemEncontrado = itemService.buscaPeloId(id);
		ListaEntity listaEncontrada = listaService.buscaPeloId(itemInput.getListaId());
		itemConvert.copiarInputParaEntity(itemInput,listaEncontrada ,itemEncontrado);
		return itemConvert.entityParaComListaOutput(itemService.atualiza(itemEncontrado));
	}


	//(K) Deleta 
	@Operation(summary = "Deleta Item pelo Id", description = "EndPoint usado para deletar um Item pelo Id")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletaPeloId(@Parameter(description = "Id do Item", example = "1") @PathVariable Long id) {
		itemService.deletaPeloId(id);
	}

}
