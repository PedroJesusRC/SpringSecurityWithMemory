package com.pjrc.SpringSecurityWithMemory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/", "/home").permitAll()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/teacher").hasAnyRole("TEACHER","ADMIN")
		.antMatchers("/student").hasAnyRole("STUDENT","ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login").permitAll()
		.and()
		.logout().permitAll();
		http.exceptionHandling().accessDeniedPage("/403");
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
        .withUser("student").password("1234").roles("STUDENT")
        .and()
        .withUser("admin").password("1234").roles("ADMIN")
        .and()
        .withUser("teacher").password("1234").roles("TEACHER");
    }

}