package com.mpolitakis.datawise.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;


        // @Bean
        // @Order(1)                                                        
        // public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
	// 	http
	// 		.securityMatcher("/api/v1/products/**")                                   
	// 		.authorizeHttpRequests(authorize -> authorize
	// 			.anyRequest().hasRole("ADMIN")
	// 		)
	// 		.authenticationProvider(authenticationProvider)
        //         .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	// 	return http.build();
	// }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .csrf(csrf -> csrf
                      .disable())
              .authorizeHttpRequests()
              .requestMatchers(
                      "/api/v1/auth/**",
                      "/api/v1/products/**",
                      "/v2/api-docs",
                      "/v3/api-docs",
                      "/v3/api-docs/**",
                      "/swagger-resources",
                      "/swagger-resources/**",
                      "/configuration/ui",
                      "/configuration/security",
                      "/swagger-ui/**",
                      "/webjars/**",
                      "/swagger-ui.html"
              )
              .permitAll()

              .anyRequest()
              .authenticated()
              .and()
              .sessionManagement(management -> management
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authenticationProvider(authenticationProvider)
              .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
              .logout(logout -> logout
                      .logoutUrl("/api/v1/auth/logout")
                      .addLogoutHandler(logoutHandler)
                      .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
    ;

    return http.build();
  }
}
