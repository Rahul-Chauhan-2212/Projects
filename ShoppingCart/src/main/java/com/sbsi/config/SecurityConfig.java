package com.sbsi.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.DelegatingRequestMatcherHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sbsi.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setHideUserNotFoundExceptions(false);

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER").antMatchers("/admin/**")
				.hasAuthority("ADMIN")
				.antMatchers("/index.xhtml", "/signup.xhtml", "/access-denied.xhtml", "/resources/css/**",
						"/resources/images/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/index.xhtml")
				.usernameParameter("username").passwordParameter("password").loginProcessingUrl("/login").and().logout()
				.logoutSuccessUrl("/index.xhtml").invalidateHttpSession(true).deleteCookies("JSESSIONID").and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and().csrf().disable();

		http.headers().addHeaderWriter(new DelegatingRequestMatcherHeaderWriter(
				new AntPathRequestMatcher("/javax.faces.resource/**"), new HeaderWriter() {
					@Override
					public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
						response.addHeader("Cache-Control", "private, max-age=86400");
					}
				})).defaultsDisabled();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1)
				.expiredUrl("/index.xhtml?invalid-session=true");
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
}