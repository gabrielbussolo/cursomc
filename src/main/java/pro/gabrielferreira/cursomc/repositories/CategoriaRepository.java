package pro.gabrielferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pro.gabrielferreira.cursomc.domain.Categoria;

/*
 * @repository é tipo DAO, nao fiz nada aqui ainda, só herdei Jpa, 
 * passo pra ele a classe de dominio e o tipo do Id dela
 * JpaRepository ja tem varios metodos prontos, find, findall, getbyid. ps:tops
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	
}
