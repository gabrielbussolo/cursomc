package pro.gabrielferreira.cursomc.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.domain.Produto;

//vide CategoriaRepository
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	// Consulta JPQL personalizada

	/*
	 * @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat
	 * WHERE obj. nome LIKE%:nome% AND cat IN:categorias")
	 * 
	 * Page<Produto> search(@Param("nome") String nome, @Param("categorias")
	 * List<Categoria> categorias, Pageable pageRequest);
	 */
	@Transactional(readOnly = true) // anotacao informando que o que eu quero é readonly
	// spring capirotesco gera jpql apenas usando os nomes corretos
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias,
			Pageable pageRequest);
}
