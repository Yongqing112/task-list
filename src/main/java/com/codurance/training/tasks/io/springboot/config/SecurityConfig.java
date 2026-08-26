package com.codurance.training.tasks.io.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/h2-console/**").permitAll()
				.anyRequest().authenticated()
		);
		http.csrf(csrf -> csrf
				.ignoringRequestMatchers("/h2-console/**")
		);
		http.headers(headers -> headers
				.frameOptions(frameOptions -> frameOptions
						.sameOrigin()
				)
		);
		return http.build();
	}
}