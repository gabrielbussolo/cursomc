package pro.gabrielferreira.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import pro.gabrielferreira.cursomc.services.DBService;

@Configuration
@Profile("dev") // configuracoes para o perfil de teste
public class DevConfig {
	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws Exception {
		// verifica qual a estrategia de geracao de banco do .properties
		if (!"create".equals(strategy)) {
			return false;
		}
		dbService.instanciateTestDatabase();
		return true;
	}
}
