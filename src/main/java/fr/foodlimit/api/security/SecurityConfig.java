package fr.foodlimit.api.security;

import fr.foodlimit.api.security.jwt.JWTConfigurer;
import fr.foodlimit.api.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Profile({ "dev", "prod" })
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
      .antMatchers("/authenticate").permitAll()
      .antMatchers("/signup").permitAll()
      .antMatchers("/login").permitAll()
      .antMatchers("/public").permitAll()
      .antMatchers("/ping").permitAll()
      .antMatchers("/h2/**").permitAll()
      .antMatchers("/v2/api-docs/**").permitAll()
      .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
      .anyRequest().authenticated()
      .and()
      .apply(new JWTConfigurer(this.tokenProvider));
  }

}
