package pro.gabrielferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pro.gabrielferreira.cursomc.domain.Produto;

//vide CategoriaRepository
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
