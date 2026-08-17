package com.codurance.training.tasks.io.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// add this line to use H2 web console
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/console/**")
				.permitAll());
		http.csrf(csrf -> csrf.disable());
		http.headers(header -> header.frameOptions(
				frameOption -> frameOption.disable()));

		return http.build();
	}
}