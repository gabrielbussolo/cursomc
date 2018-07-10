package pro.gabrielferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pro.gabrielferreira.cursomc.domain.ItemPedido;

//repository normal
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer>{
	
}
