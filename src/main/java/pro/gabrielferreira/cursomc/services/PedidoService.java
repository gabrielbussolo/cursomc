package pro.gabrielferreira.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.gabrielferreira.cursomc.domain.Pedido;
import pro.gabrielferreira.cursomc.repositories.PedidoRepository;
import pro.gabrielferreira.cursomc.services.exceptions.ObjectNotFoundException;

//aqui é onde ficam as regras de negocio
@Service
public class PedidoService {

	@Autowired // instancia automaticamente a dependencia
	private PedidoRepository repo;

	// ou seja, essa é uma "regra" que quando der um buscar, vai retornar um item
	// por id
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		// reparar a separacao das responsabilidades, por exemplo aqui instanciei um
		// repo ali em cima, e uso o metodo dele de buscar no banco por id, cada classe
		// com sua responsabilidade
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
