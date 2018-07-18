package pro.gabrielferreira.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import pro.gabrielferreira.cursomc.services.DBService;

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
}
