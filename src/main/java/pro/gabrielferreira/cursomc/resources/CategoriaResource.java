package pro.gabrielferreira.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.services.CategoriaService;

//@restcontroler, digo que essa classe é um controller
@RestController
@RequestMapping(value="/categorias") //digo qual a rota dela (endpoint)
public class CategoriaResource {
	
	//Instancia a classe a baixo
	@Autowired
	private CategoriaService service;
	
	//novamente rota, porem informo pra qual metodo ela vai responder, ou seja posso ter um metodo post e get na mesma rota fazendo coisas diferentes.
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(/*anotacao pra dizer que to pegando o valor pela URI*/@PathVariable Integer id) {
		Categoria obj = service.buscar(id);	//novamente a separacao de responsabilidades vide categoria service.
		//chamo categoria service, que chama categoria resource, cada classe com sua responsabilidade
		return ResponseEntity.ok().body(obj);
	}
	
	//metodo post para inserir categoria
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody /*anotacao pra popular obj com json */Categoria obj){
		obj = service.insert(obj); //insere no banco de dados
		//objeto do tipo URI pra criar e retornar a uri do objeto salvo
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build(); //responde com http 201 e retorna a url do obj criado
	}
}
