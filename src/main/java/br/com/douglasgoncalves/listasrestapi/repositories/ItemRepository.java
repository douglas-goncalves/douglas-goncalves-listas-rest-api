package br.com.douglasgoncalves.listasrestapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.douglasgoncalves.listasrestapi.entities.ItemEntity;
import br.com.douglasgoncalves.listasrestapi.entities.ListaEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
	List<ItemEntity> findAllByLista(ListaEntity lista);
	
	@Query("SELECT i FROM ItemEntity i WHERE i.id = :itenId AND lista.id = :listaId")
	Optional<ItemEntity> findBylistaIdAnditemId(@Param("listaId") Long listaId, @Param("itenId") Long itenId);

}
