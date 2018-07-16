package pro.gabrielferreira.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pro.gabrielferreira.cursomc.domain.Cliente;
import pro.gabrielferreira.cursomc.domain.enums.TipoCliente;
import pro.gabrielferreira.cursomc.dto.ClienteNewDTO;
import pro.gabrielferreira.cursomc.repositories.ClienteRepository;
import pro.gabrielferreira.cursomc.resources.exceptions.FieldMessage;
import pro.gabrielferreira.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		/* start - unica parte construida */
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		// instancio um cliente buscando ele pelo email (qeu ja estou recebendo no
		// metodo)
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) { // se o obj voltou diferente de nulo é pq ja ta cadastrado
			list.add(new FieldMessage("email", "Email já existe"));
		}
		/* end */
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}