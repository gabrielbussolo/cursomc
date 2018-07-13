package pro.gabrielferreira.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import pro.gabrielferreira.cursomc.domain.Cidade;
import pro.gabrielferreira.cursomc.domain.Cliente;
import pro.gabrielferreira.cursomc.domain.Endereco;
import pro.gabrielferreira.cursomc.domain.enums.TipoCliente;
import pro.gabrielferreira.cursomc.dto.ClienteDTO;
import pro.gabrielferreira.cursomc.dto.ClienteNewDTO;
import pro.gabrielferreira.cursomc.repositories.ClienteRepository;
import pro.gabrielferreira.cursomc.repositories.EnderecoRepository;
import pro.gabrielferreira.cursomc.services.exceptions.DataIntegrityException;
import pro.gabrielferreira.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	ClienteRepository repo;

	@Autowired
	EnderecoRepository repoEndereco;
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException( // excessao que eu criei
				"Objeto não encontrado ! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		repo.save(obj);
		repoEndereco.saveAll(obj.getEnderecos());
		return obj;
	}
	// atualiza obj no banco, igual salva
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId()); // procuro o obj antes de salvar, por garantia
		updateData(newObj, obj);
		return repo.save(newObj); // salva
	}

	// deleta obj do banco pelo id
	public void delete(Integer id) {
		find(id); // procura obj antes de deletar
		try {
			repo.deleteById(id); // deleta por id
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir pois ha entidades relacionadas");
			// msg personalizada caso de exeption
		}
	}

	// retorna tudo do banco, joao mostrou como fazer uma query e pegar só o que eu
	// quero.
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	// metodo para paginacao das categorias
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		// instancio o objeto pagereq com a forma que eu quero que o repo traga do banco
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		// passo pro repo a forma que eu quero que ele traga do banco pra mim.
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cid = new Cidade(objDTO.getCidadeId(),null,null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
