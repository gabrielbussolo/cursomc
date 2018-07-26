package pro.gabrielferreira.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ls.LSInput;

import pro.gabrielferreira.cursomc.domain.Cidade;
import pro.gabrielferreira.cursomc.domain.Estado;
import pro.gabrielferreira.cursomc.dto.CidadeDTO;
import pro.gabrielferreira.cursomc.dto.EstadoDTO;
import pro.gabrielferreira.cursomc.repositories.CidadeRepository;
import pro.gabrielferreira.cursomc.repositories.EstadoRepository;
import pro.gabrielferreira.cursomc.services.CidadeService;
import pro.gabrielferreira.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {
	@Autowired
	private EstadoService estadoService;

	@Autowired
	private CidadeService cidadeService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> listEstados = estadoService.findAll();

		List<EstadoDTO> listEstadosDTO = listEstados.stream().map(obj -> new EstadoDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listEstadosDTO);
	}

	@RequestMapping(value = "/{idEstado}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer idEstado) {
		List<Cidade> listCidade = cidadeService.findByEstado(idEstado);
		List<CidadeDTO> listCidadeDTO = listCidade.stream()//
				.map(obj -> new CidadeDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listCidadeDTO);
	}
}
