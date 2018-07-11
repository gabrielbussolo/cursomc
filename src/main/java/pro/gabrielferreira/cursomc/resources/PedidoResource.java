package pro.gabrielferreira.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pro.gabrielferreira.cursomc.domain.Pedido;
import pro.gabrielferreira.cursomc.services.PedidoService;

//@restcontroler, digo que essa classe é um controller
@RestController
@RequestMapping(value="/pedidos") //digo qual a rota dela (endpoint)
public class PedidoResource {
	
	//Instancia a classe a baixo
	@Autowired
	private PedidoService service;
	
	//novamente rota, porem informo pra qual metodo ela vai responder, ou seja posso ter um metodo post e get na mesma rota fazendo coisas diferentes.
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(/*anotacao pra dizer que to pegando o valor pela URI*/@PathVariable Integer id) {
		Pedido obj = service.find(id);	//novamente a separacao de responsabilidades vide categoria service.
		//chamo categoria service, que chama categoria resource, cada classe com sua responsabilidade
		return ResponseEntity.ok().body(obj);
	}
}
