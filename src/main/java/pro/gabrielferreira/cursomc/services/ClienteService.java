package pro.gabrielferreira.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pro.gabrielferreira.cursomc.domain.Cidade;
import pro.gabrielferreira.cursomc.domain.Cliente;
import pro.gabrielferreira.cursomc.domain.Endereco;
import pro.gabrielferreira.cursomc.domain.enums.Perfil;
import pro.gabrielferreira.cursomc.domain.enums.TipoCliente;
import pro.gabrielferreira.cursomc.dto.ClienteDTO;
import pro.gabrielferreira.cursomc.dto.ClienteNewDTO;
import pro.gabrielferreira.cursomc.repositories.ClienteRepository;
import pro.gabrielferreira.cursomc.repositories.EnderecoRepository;
import pro.gabrielferreira.cursomc.security.UserSS;
import pro.gabrielferreira.cursomc.services.exceptions.AuthorizationException;
import pro.gabrielferreira.cursomc.services.exceptions.DataIntegrityException;
import pro.gabrielferreira.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private S3Service s3service;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	ClienteRepository repo;

	@Autowired
	EnderecoRepository repoEndereco;
	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated(); //pega uruario logado
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
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
			throw new DataIntegrityException("Não é possivel excluir pois ha pedidos relacionados");
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
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha())); //encoda a senha
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
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3service.uploadFile(imageService.getInputStream(jpgImage, "jpg"),fileName, "image");
	}
}
