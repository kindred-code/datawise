package com.mpolitakis.datawise.config;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.mpolitakis.datawise.Sec.services.JwtConfiguration;
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {


private final JwtConfiguration jwtConfiguration;

  

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http



                              .csrf(csrf -> csrf
                                      .disable())
                              .authorizeHttpRequests()
                              .requestMatchers(
                                      "/api/v1/auth/**"
                              )
                              .permitAll()
                              .requestMatchers("/api/v1/products/**").authenticated()
                              .and()
                              .oauth2ResourceServer()
                              .jwt()
                        .jwtAuthenticationConverter( jwtConfiguration.jwtAuthenticationConverter())
                              
              
              
              
    ;

    return http.build();
  }
}
