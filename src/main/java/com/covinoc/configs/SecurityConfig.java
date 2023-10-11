package com.covinoc.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Configura la seguridad de la aplicación
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;

	/**
	 * Configura la autenticación y autorización de la aplicación, 
	 * especificando cómo se gestionan las solicitudes de seguridad.
	 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/auth/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(authenticationManagerBean(), jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Proporciona un bean de administración de autenticación que permite la inyección de AuthenticationManager.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    /**
     * Configura un administrador de detalles de usuario en memoria, 
     * utilizado para autenticación en la aplicación.
     */
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User
                .withUsername("admin")
                .password("{noop}admin") // {noop} indica que la contraseña no está cifrada
                .roles("USER")
                .build();
        System.out.print(user);
        return new InMemoryUserDetailsManager(user);
    }

}