package fr.foodlimit.api.security;

import fr.foodlimit.api.security.jwt.JWTConfigurer;
import fr.foodlimit.api.security.jwt.TokenProvider;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.annotation.WebServlet;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final TokenProvider tokenProvider;

  public SecurityConfig(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .headers()
      .frameOptions()
      .disable()
      .and()
      .csrf()
      .disable()
      .cors()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
      .antMatchers("/signup").permitAll()
      .antMatchers("/login").permitAll()
      .antMatchers("/public").permitAll()
      .antMatchers("/ping").permitAll()
      .antMatchers("/h2/**").permitAll()
      .anyRequest().authenticated()
      .and()
      .apply(new JWTConfigurer(this.tokenProvider));
  }

}
