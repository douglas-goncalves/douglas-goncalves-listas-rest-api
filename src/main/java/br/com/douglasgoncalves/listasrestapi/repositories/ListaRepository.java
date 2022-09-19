package br.com.douglasgoncalves.listasrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.douglasgoncalves.listasrestapi.entities.ItemEntity;
import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;

public interface ListaRepository extends JpaRepository<ListaEntity, Long> {

}
