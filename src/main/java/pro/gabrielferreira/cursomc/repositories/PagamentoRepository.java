package pro.gabrielferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pro.gabrielferreira.cursomc.domain.Pagamento;

@Repository //nada demais, igual aos outros
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{
	
}
