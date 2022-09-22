package br.com.douglasgoncalves.listasrestapi.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;
import br.com.douglasgoncalves.listasrestapi.exceptions.NotFoundBussinessException;
import br.com.douglasgoncalves.listasrestapi.repositories.ListaRepository;

@Service
public class ListaService {
	
	@Autowired
	private ListaRepository listaRepository;
	
	@Transactional
	public ListaEntity cadastrar(ListaEntity listaEntity) {
		return listaRepository.save(listaEntity);
	}

	public List<ListaEntity> listaTodos() {
		return listaRepository.findAll();
	}

	public ListaEntity buscaPeloId(Long id) {
		return listaRepository.findById(id).orElseThrow(() -> 
		new NotFoundBussinessException(String.format("A lista de id:%s não foi Encontrado", id)));
	}

	@Transactional
	public void deletaPeloId(Long id) {
		listaRepository.delete(this.buscaPeloId(id));
	}

	@Transactional
	public ListaEntity atualiza(ListaEntity listaEntity) {
		return listaRepository.save(listaEntity);
	}

}
