package pro.gabrielferreira.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import pro.gabrielferreira.cursomc.domain.Pedido;

//interface com metodos obrigatorios para o envio de email ("contrato") pra quem quiser fazer um email service
public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
