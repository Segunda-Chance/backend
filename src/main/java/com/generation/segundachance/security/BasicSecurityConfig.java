package com.generation.segundachance.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BasicSecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	UserDetailsService userDetailsService() {

		return new UserDetailsServiceImpl();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(csrf -> csrf.disable()).cors(withDefaults());

		http.authorizeHttpRequests((auth) -> auth.requestMatchers("/usuarios/logar", "/usuarios/cadastrar", "/error/**").permitAll()
				.requestMatchers("/categorias").permitAll()
				.requestMatchers("/categorias/{id}").permitAll()
				.requestMatchers("/categorias/tipo/{tipo}").permitAll()
				.requestMatchers("/categorias/nomeCategoria/{nomeCategoria}").permitAll()
				.requestMatchers("/produto").permitAll()
				.requestMatchers("/produto/{id}").permitAll()
				.requestMatchers("/produto/nomeProduto/{nomeProduto}").permitAll()
				.requestMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).httpBasic(withDefaults());

		return http.build();
	}
}
