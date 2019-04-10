package hr.java.web.petkovic.moneyapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	protected PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth
			.inMemoryAuthentication()
			.withUser("admin")
			.password(getPasswordEncoder().encode("admin"))
			.roles("ADMIN", "USER")
			.and()
			.withUser("oper")
			.password(getPasswordEncoder().encode("oper"))
			.roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.authorizeRequests()
			.antMatchers("/troskovi/role")
			.hasRole("ADMIN")
			.antMatchers("/troskovi/**")
			.hasRole("USER")
			.antMatchers("**")
			.permitAll()
			.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/troskovi/novitrosak")
			.and()
			.logout()
			.logoutSuccessUrl("/login");
	}

}
