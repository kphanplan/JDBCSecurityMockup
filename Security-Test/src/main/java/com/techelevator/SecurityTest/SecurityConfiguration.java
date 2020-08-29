package com.techelevator.SecurityTest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
//allows you get access to the authentication manger builder
//that allows you to configure JDBC authentication 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	// sets up a datasource
	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// now that you've got everything set up, you select jdbcAuthentication.
		// now we inject the datasource into the authentication--the H2 Database
		// using usersByUsernameQuery, lets you pass queries to get users and authorities.
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password,enabled from users where username = ?")
				.authoritiesByUsernameQuery("select username,authority from authorities where username = ?");
	}

	// IDK WTF IS HAPPENING HERE
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// "/admin" can only be accessed by admin
				.antMatchers("/admin").hasRole("ADMIN")
				// "/user can only be accessed by user"
				.antMatchers("/user").hasAnyRole("ADMIN", "USER")
				// "/" can be accessed by all.
				.antMatchers("/").permitAll()
				// prompt a login form?
				.and().formLogin();
	}

	// set up a password encoder (idk what this does)
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
