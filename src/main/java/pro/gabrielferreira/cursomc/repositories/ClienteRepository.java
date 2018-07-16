package pro.gabrielferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pro.gabrielferreira.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	@Transactional(readOnly = true) // diminui "locking" do banco de dados, faz ficar mais rapido por se tratar
									// apenas de uma consulta
	Cliente findByEmail(String email); // importancia do padrao de nomes, find+by+email o spring cria ja um metodo pra
										// pegar o email do banco (pica demais)
}
