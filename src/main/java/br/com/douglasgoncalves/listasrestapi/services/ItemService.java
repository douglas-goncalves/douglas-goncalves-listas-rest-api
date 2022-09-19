package br.com.douglasgoncalves.listasrestapi.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.douglasgoncalves.listasrestapi.entities.ItemEntity;
import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;
import br.com.douglasgoncalves.listasrestapi.exceptions.NotFoundBussinessException;
import br.com.douglasgoncalves.listasrestapi.repositories.ItemRepository;
import io.swagger.v3.oas.annotations.Parameter;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	@Transactional
	public ItemEntity cadastra(ItemEntity itemEntity) {
		return itemRepository.save(itemEntity);
	}

	public List<ItemEntity> listaTodos() {
		return itemRepository.findAll();
	}

	public ItemEntity buscaPeloId(Long id) {
		return itemRepository.findById(id).orElseThrow(() -> 
		new NotFoundBussinessException(String.format("O Item de id:%s não foi Encontrado", id)));
	}

	@Transactional
	public void deletaPeloId(Long id) {
		itemRepository.delete(this.buscaPeloId(id));
	}

	@Transactional
	public ItemEntity atualiza(ItemEntity itemEntity) {
		return itemRepository.save(itemEntity);
	}

	public List<ItemEntity> listaItensPelaLista(ListaEntity lista) {
		return itemRepository.findAllByLista(lista);
	}

	@Transactional
	public void deletaTodosItensDaLista(ListaEntity lista) {
		itemRepository.findAllByLista(lista).forEach(item ->{
			this.deletaPeloId(item.getId());
		});
	}

	public ItemEntity buscaItemPeloListaIdItemId(Long listaId, Long itemId) {
		return itemRepository.findBylistaIdAnditemId(listaId, itemId).orElseThrow(()->
		new NotFoundBussinessException(String.format("O Item de id:%s da Lista id:%s não foi Encontrado", itemId, listaId)));	
	}

	@Transactional
	public void marcarComoConcluido(ItemEntity itemEntity) {
		itemEntity.setConcluido(true);
		this.itemRepository.save(itemEntity);
	}
	
	@Transactional
	public void desmarcarConcluido(ItemEntity itemEntity) {
		itemEntity.setConcluido(false);
		this.itemRepository.save(itemEntity);
	}

}
