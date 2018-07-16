package pro.gabrielferreira.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import pro.gabrielferreira.cursomc.domain.Cliente;
import pro.gabrielferreira.cursomc.dto.ClienteDTO;
import pro.gabrielferreira.cursomc.repositories.ClienteRepository;
import pro.gabrielferreira.cursomc.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	@Autowired
	private HttpServletRequest req;

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		// pego o id do cliente, pela URI da requisicao atual.
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) req
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		List<FieldMessage> list = new ArrayList<>();
		/* start - unica parte construida */

		// instancio um cliente buscando ele pelo email (qeu ja estou recebendo no
		// metodo)
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) { // se o obj voltou diferente de nulo é pq ja ta cadastrado
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