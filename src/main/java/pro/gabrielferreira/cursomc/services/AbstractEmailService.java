package pro.gabrielferreira.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import pro.gabrielferreira.cursomc.domain.Pedido;

//implementa a interface de forma abstrata, apenas para implementar o metodo "principal" como prepareSimpleMailMessageFromPedido
public abstract class AbstractEmailService implements EmailService {

	// pega valor do properties
	@Value("${default.sender}")
	private String sender;

	// metodo para enviar o email
	public void sendOrderConfirmationEmail(Pedido obj) { // recebe o pedido
		// cria variavel simplemail, usa o metodo pra preparar o pedido pra email, passa
		// pra variavel
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm); // envia o email (metodo ainda nao implementado)
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
}
