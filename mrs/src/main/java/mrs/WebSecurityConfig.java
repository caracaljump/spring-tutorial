package mrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import mrs.domain.service.user.ReservationUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	@Autowired
	ReservationUserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				authz -> authz.antMatchers("/js/**", "/css/**").permitAll().antMatchers("/**").authenticated())
				.formLogin(login -> login.loginPage("/loginForm").loginProcessingUrl("/login")
						.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/rooms", true)
						.failureUrl("/loginForm?error=true").permitAll())
				.logout(logout -> logout.logoutSuccessUrl("/"));
		return http.build();
	}
}
