package pro.gabrielferreira.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import pro.gabrielferreira.cursomc.domain.Cliente;
import pro.gabrielferreira.cursomc.domain.Pedido;

//implementa a interface de forma abstrata, apenas para implementar o metodo "principal" como prepareSimpleMailMessageFromPedido
public abstract class AbstractEmailService implements EmailService {

	// pega valor do properties
	@Value("${default.sender}")
	private String sender;

	// dependencias necessarias
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	// metodo para enviar o email
	public void sendOrderConfirmationEmail(Pedido obj) { // recebe o pedido
		// cria variavel simplemail, usa o metodo pra preparar o pedido pra email, passa
		// pra variavel
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm); // envia o email (metodo ainda nao implementado)
	}

	// envia o email com o sendHtmlEmail, caso quebre usa o template antigo
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		MimeMessage mm;
		try {
			mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}

	}

	// prepara o email para ser enviado com HTML
	private MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}

	// prepara o email para ser enviado
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		// instancia o obj simple email
		SimpleMailMessage sm = new SimpleMailMessage();
		// seta configs pra ele
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm; // retorna o obj ja configurado e pronto
	}

	// processa o template thymeleaf
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj); // pedido é o nome da variavel que esta sendo usada no thymeleaf
		return templateEngine.process("email/confirmacaoPedido", context); // passo o template e processo ele com o
																			// templateEngine
	}
	
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		// instancia o obj simple email
		SimpleMailMessage sm = new SimpleMailMessage();
		// seta configs pra ele
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitacao de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm; // retorna o obj ja configurado e pronto
	}
}
