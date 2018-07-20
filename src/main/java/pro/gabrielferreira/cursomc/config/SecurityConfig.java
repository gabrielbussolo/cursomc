package pro.gabrielferreira.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//dependencia pra pegar o enviroment para funcionar o h2
	@Autowired
	private Environment env;
	
	//rota livre
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**",
	};
	
	//rotas que só vai rolar get
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//apenas para h2-console, se tiver no env test.
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();
		//rotas autorizadas
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() //get autorizado
				.antMatchers(PUBLIC_MATCHERS).permitAll() //tudo autorizado
				.anyRequest().authenticated(); //para o resto precisa estar autenticado
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //nao usa sessao
	}
	
	//configuracao pra permitir requisicao cross-platform
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	//bean de configuracao de criptografia
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
