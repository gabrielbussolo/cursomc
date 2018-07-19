package pro.gabrielferreira.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

//implementa a classe AbstractEmailService igual o mock
public class SmtpEmailService extends AbstractEmailService {

	// injeta as dependencias de email que sao as configuracoes no properties
	@Autowired
	private MailSender mailSender;

	@Autowired
	private JavaMailSender javaMailSender;

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg); // envia realmente o email
		LOG.info("Email enviado");
	}

	@Override // implementacao do envio de email html
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email...");
		javaMailSender.send(msg); // envia realmente o email
		LOG.info("Email enviado");

	}

}
