package com.mpolitakis.api.Sec.services;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Slf4j
@Component
@Configuration
public class JwtConfiguration {


  
  @Value("${spring.application.security.jwt.keystore-location}")
	private String keyStorePath;
	
	@Value("${spring.application.security.jwt.keystore-password}")
	private String keyStorePassword;
	
	@Value("${spring.application.security.jwt.key-alias}")
	private String keyAlias;
	



@Bean
	public KeyStore keyStore() throws java.io.IOException {
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
			keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
			return keyStore;
		} catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
			log.error("Unable to load keystore: {}", keyStorePath, e);
		}
		
		throw new IllegalArgumentException("Unable to load keystore");
	}
	
	@Bean
	public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
		try {
			Key key = keyStore.getKey(keyAlias, keyStorePassword.toCharArray());
			if (key instanceof RSAPrivateKey) {
				return (RSAPrivateKey) key;
			}
		} catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
			log.error("Unable to load private key from keystore: {}", keyStorePath, e);
		}
		
		throw new IllegalArgumentException("Unable to load private key");
	}
	
	@Bean
	public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
		try {
			Certificate certificate = keyStore.getCertificate(keyAlias);
			PublicKey publicKey = certificate.getPublicKey();
			
			if (publicKey instanceof RSAPublicKey) {
				return (RSAPublicKey) publicKey;
			}
		} catch (KeyStoreException e) {
			log.error("Unable to load private key from keystore: {}", keyStorePath, e);
		}
		
		throw new IllegalArgumentException("Unable to load RSA public key");
	}
	
	@Bean
	public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
		return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
			final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
			// here choose a claim name where you stored authorities on login (defaults to "scope" and "scp" if not used)
			grantedAuthoritiesConverter.setAuthoritiesClaimName("Authorities");
			// here choose a scope prefix (defaults to "SCOPE_" if not used)
			grantedAuthoritiesConverter.setAuthorityPrefix("");

			final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
			jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
			return jwtAuthenticationConverter;
	}

	public String createJwtForClaims(String subject, Set<String> claims) throws IllegalArgumentException, JWTCreationException, IOException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Instant.now().toEpochMilli());
		calendar.add(Calendar.DATE, 1);
		
		JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);
		String[] arrayClaims = new String[claims.size()];
		int index = 0;
        for (String claim : claims)
            arrayClaims[index++] = claim;
		
		
		
		
		return jwtBuilder
				.withNotBefore(new Date())
				.withExpiresAt(calendar.getTime())
				.withArrayClaim("Authorities", arrayClaims)
				.sign(Algorithm.RSA256(jwtValidationKey(keyStore()), jwtSigningKey(keyStore())));
	}
}