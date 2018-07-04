package pro.gabrielferreira.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.services.CategoriaService;

//@restcontroler, digo que essa classe é um controller
@RestController
@RequestMapping(value="/categorias") //digo qual a rota dela (endpoint)
public class CategoriaResource {
	
	//Instancia a classe a baixo
	@Autowired
	private CategoriaService service;
	
	//novamente rota, porem informo pra qual metodo ela vai responder, ou seja se eu der um post aqui, nao faz nada.
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria obj = service.buscar(id);	
		return ResponseEntity.ok().body(obj);
	}
}
