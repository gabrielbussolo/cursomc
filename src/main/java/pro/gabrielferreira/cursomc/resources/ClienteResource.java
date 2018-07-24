package pro.gabrielferreira.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pro.gabrielferreira.cursomc.domain.Cliente;
import pro.gabrielferreira.cursomc.dto.ClienteDTO;
import pro.gabrielferreira.cursomc.dto.ClienteNewDTO;
import pro.gabrielferreira.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	// adicionado @valid pra validar o obj que esta chegando
	public ResponseEntity<Void> insert(
			@Valid @RequestBody /* anotacao pra popular obj com json */ClienteNewDTO objDTO) {
		Cliente obj = service.fromDTO(objDTO); // converte o DTO pra Categoria
		obj = service.insert(obj); // insere no banco de dados
		// objeto do tipo URI pra criar e retornar a uri do objeto salvo
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build(); // responde com http 201 e retorna a url do obj criado
	}

	// metodo put para atualizar categoria
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	// pego json no request e instancio um obj, e pego o id pela url -- anotacao
	// @valid par validar o objDTO
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDTO); // converte DTO pra Cliente
		obj.setId(id); // só por garantia, seto o id no obj
		obj = service.update(obj); // update do obj no banco
		return ResponseEntity.noContent().build();
	}

	// deleta categoria pegando pelo id da URI
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Cliente> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	// retorna uma lista com as categorias
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		// retorna uma lista de objetos categorias, só que categoria *entidade* tem uma
		// lista de objetos produtos nela, que volta junto
		List<Cliente> objList = service.findAll();
		// pra resolver, usa-se um DTO com apenas os dados que quero
		// essa linha cria uma lista de ClienteDTO
		List<ClienteDTO> listDTO = objList.stream() //
				.map(obj -> new ClienteDTO(obj)) //
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(//
			// parametros da requisicao e valores default caso nao seja informado
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		// retorna uma lista com as categorias na forma que foi passado por parametro
		Page<Cliente> objList = service.findPage(page, linesPerPage, orderBy, direction);
		// percorro o objList e instancio um DTO pra tirar os produtos associados a
		// categoria.
		Page<ClienteDTO> listDTO = objList.map(obj -> new ClienteDTO(obj));

		return ResponseEntity.ok().body(listDTO);
	}
}
