package br.com.loja.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/css/**", "/").permitAll()
				.requestMatchers("/listar", "/novo", "/mostrar/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/salvar", "/excluir/**").hasRole("ADMIN")
			    .anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/login").permitAll());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails funcionario = User.builder().username("pedro").password(passwordEncoder().encode("123"))
				.roles("USER").build();

		UserDetails administrador = User.builder().username("breno").password(passwordEncoder().encode("123"))
				.roles("ADMIN").build();

		return new InMemoryUserDetailsManager(funcionario, administrador);
	}
}
