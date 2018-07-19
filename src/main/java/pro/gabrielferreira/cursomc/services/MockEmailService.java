package pro.gabrielferreira.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

//extends abstractemail apensar pra mudar a forma de enviar o email, que ja esta sendo usado na classe abstrata
public class MockEmailService extends AbstractEmailService {
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override // classe de send email usada no abstract
	public void sendEmail(SimpleMailMessage msg) {// implementacao da classe apenas para apresentar no console
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		LOG.info("Email enviado");

	}

}
