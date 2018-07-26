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

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> listEstados = estadoRepository.findAllByOrderByNome();

		List<EstadoDTO> listEstadosDTO = listEstados.stream().map(obj -> new EstadoDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listEstadosDTO);
	}

	@RequestMapping(value = "/{idEstado}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer idEstado) {

		List<Cidade> listCidade = cidadeRepository.findAll();

		List<CidadeDTO> listCidadeDTO = listCidade.stream()//
				.filter(obj -> obj.getEstado().getId() == idEstado)//
				.map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listCidadeDTO);
	}
}
