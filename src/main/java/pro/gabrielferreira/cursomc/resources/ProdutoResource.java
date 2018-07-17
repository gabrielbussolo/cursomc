package pro.gabrielferreira.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pro.gabrielferreira.cursomc.domain.Produto;
import pro.gabrielferreira.cursomc.dto.ProdutoDTO;
import pro.gabrielferreira.cursomc.resources.utils.URL;
import pro.gabrielferreira.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}

	// responde o get de produtos
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(//
			// parametros que posso receber pela URI
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		String nomeDecoded = URL.decodeParam(nome);// decoda os espacos que pode ter sido mandado pela URI
		List<Integer> ids = URL.decodeIntList(categorias); // separa os ids que vem no formato string

		// pede pro service procurar
		Page<Produto> objList = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);

		// para cada Produto que voltou em objList crio uma lista de ProdutoDTO
		Page<ProdutoDTO> listDTO = objList.map(obj -> new ProdutoDTO(obj));

		return ResponseEntity.ok().body(listDTO);
	}
}
