package com.hireling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MyConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new WorkerServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provide=new DaoAuthenticationProvider();
		provide.setUserDetailsService(this.userDetailsService());
		provide.setPasswordEncoder(passwordEncoder());
		return provide;
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/admin/**")
		.hasRole("ADMIN")
		.requestMatchers("/worker/**")
		.hasRole("WORKER")
		.requestMatchers("/**")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/signin")
		.loginProcessingUrl("/signin")
		.defaultSuccessUrl("/worker/index");
		httpSecurity.authenticationProvider(daoAuthenticationProvider());
		return httpSecurity.build();
	}
}
