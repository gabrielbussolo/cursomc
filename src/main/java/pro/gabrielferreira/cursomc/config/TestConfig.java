package pro.gabrielferreira.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import pro.gabrielferreira.cursomc.services.DBService;
import pro.gabrielferreira.cursomc.services.EmailService;
import pro.gabrielferreira.cursomc.services.MockEmailService;

@Configuration
@Profile("test") // configuracoes para o perfil de teste
public class TestConfig {
	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws Exception {
		dbService.instanciateTestDatabase();
		return true;
	}

	// esse bean diz que quando eu injetar um Email service e tiver usando o profile
	// de test, ele vai retornar um mockemailservice
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
