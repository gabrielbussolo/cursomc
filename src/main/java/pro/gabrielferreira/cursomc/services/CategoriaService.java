package pro.gabrielferreira.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.repositories.CategoriaRepository;

//aqui é onde ficam as regras de negocio
@Service
public class CategoriaService {
	
	@Autowired //instancia automaticamente a dependencia
	private CategoriaRepository repo;
	
	//ou seja, essa é uma "regra" que quando der um buscar, vai retornar um item por id
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
