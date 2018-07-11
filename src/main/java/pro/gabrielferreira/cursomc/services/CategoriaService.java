package pro.gabrielferreira.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.repositories.CategoriaRepository;
import pro.gabrielferreira.cursomc.services.exceptions.DataIntegrityException;
import pro.gabrielferreira.cursomc.services.exceptions.ObjectNotFoundException;

//aqui é onde ficam as regras de negocio
@Service
public class CategoriaService {

	@Autowired // instancia automaticamente a dependencia
	private CategoriaRepository repo;

	// ou seja, essa é uma "regra" que quando der um buscar, vai retornar um item
	// por id
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		// reparar a separacao das responsabilidades, por exemplo aqui instanciei um
		// repo ali em cima, e uso o metodo dele de buscar no banco por id, cada classe
		// com sua responsabilidade
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	// salva obj no banco
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	// atualiza obj no banco, igual salva
	public Categoria update(Categoria obj) {
		find(obj.getId()); // procuro o obj antes de salvar, por garantia
		return repo.save(obj); // salva
	}
	
	//deleta obj do banco pelo id
	public void delete(Integer id) {
		find(id); //procura obj antes de deletar
		try {
			repo.deleteById(id); //deleta por id
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos"); //msg personalizada caso de exeption
		}
	}
}
