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
import br.com.douglasgoncalves.listasrestapi.dtos.inputs.ItemInput;
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

	// Cadastra
	@Operation(summary = "Cadastar um novo Item", description = "EndPoint usado para Cadastrar um novo Item")
	@PostMapping
	public ResponseEntity<ItemOutput> cadastra(
		@Parameter(description = "Representação do Item")	@Valid @RequestBody ItemInput itemInput, UriComponentsBuilder uriBuild) {
		ListaEntity lista = listaService.buscaPeloId(itemInput.getLista_id());
		ItemEntity itemNovo = itemConvert.inputParaEntity(itemInput, lista);
		ItemEntity itemSalvo = itemService.cadastra(itemNovo);
		URI uri = uriBuild.path(ControllerConfig.PRE_URL + "/itens").buildAndExpand(itemSalvo.getId()).toUri();

		return ResponseEntity.created(uri).body(itemConvert.entityParaOutput(itemSalvo));
	}

	// Lista Todos
	@Operation(summary = "Lista Todos os Item", description = "EndPoint usado para Listar todos os Itens")
	@GetMapping
	public List<ItemOutput> ListaTodos() {
		return itemConvert.listaEntityParaListaOutput(itemService.listaTodos());
	}

	// Busca Pelo id
	@Operation(summary = "Busca Item pelo Id", description = "EndPoint usado para fazer a busca de um Item pelo Id")
	@GetMapping("/{id}")
	public ItemEntity BuscaPeloId(@Parameter(description = "Id do Item", example = "1") @PathVariable Long id) {
		return itemService.buscaPeloId(id);
	}

	// Deleta
	@Operation(summary = "Deleta Item pelo Id", description = "EndPoint usado para deletar um Item pelo Id")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletaPeloId(@Parameter(description = "Id do Item", example = "1") @PathVariable Long id) {
		itemService.deletaPeloId(id);
	}

	// atualiza
	@PutMapping("/{id}")
	@Operation(summary = "Atualiza Item com os novos dados", description = "EndPoint usado para Atualizar o Item com os dados passados")
	public ItemOutput atualiza(@Parameter(description = "Id do Item", example = "1") @PathVariable Long id,
			@RequestBody ItemInput itemInput) {
		ItemEntity itemEncontrado = itemService.buscaPeloId(id);
		itemConvert.copiarInputParaEntity(itemInput, itemEncontrado);
		return itemConvert.entityParaOutput(itemService.atualiza(itemEncontrado));
	}

}
