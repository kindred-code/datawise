package com.mpolitakis.datawise.auth;


import com.auth0.jwt.exceptions.JWTCreationException;
import com.mpolitakis.datawise.Sec.services.JwtConfiguration;
import com.mpolitakis.datawise.config.ApplicationConfig;
import com.mpolitakis.datawise.user.User;
import com.mpolitakis.datawise.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtConfiguration jwtConfig;
  private final AuthenticationManager authenticationManager;

  public RegisterRequest register(RegisterRequest request) {
    var user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    repository.save(user);
    return request;
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) throws IllegalArgumentException, JWTCreationException, IOException {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    UserDetails userDetails;
    try {
        userDetails = repository.findByUsername(request.getUsername());
    } catch (UsernameNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
    }

    if (passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", request.getUsername());
    
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        claims.put(ApplicationConfig.AUTHORITIES_CLAIM_NAME, authorities);

        String jwt = jwtConfig.createJwtForClaims(request.getUsername(), claims);
        return new AuthenticationResponse(jwt);
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
}
  



}
