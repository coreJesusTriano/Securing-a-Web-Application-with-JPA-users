package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.securingweb.models.services.JpaUserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// @EnableWebSecurity to enable Spring Security’s web security support 
// and provide the Spring MVC integration.
@Configuration
@EnableWebSecurity 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/*
	 *  It also extends WebSecurityConfigurerAdapter and overrides a couple of its methods
	 *  to set some specifics of the web security configuration.
	 *  
	 *  When a user successfully logs in, they are redirected to		
	 *  // Cuando un usuario inicia sesión con éxito, se lo redirige a  
	 *  the previously requested page that required authentication.		
	 *  // la página anteriormente solicitada que requirió autenticación.
	 */
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JpaUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home", "/signup", "/adduser").permitAll()
				.anyRequest().authenticated()
				.and()
					.formLogin()
						.loginPage("/login").permitAll()
				.and()
					.logout().permitAll();
	}

	
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

		// configura el constructor de usuarios que utiliza nuestro JpaUserDetailsService
		builder.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder)
		;
	}
	
	
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		PasswordEncoder encoder = passwordEncoder();
//		UserDetails user =
//			 User.builder().passwordEncoder(encoder::encode)
//				.username("usuario")
//				.password("patata")
//				.roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(user);
//	}

	// Para codificar el password
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	
}