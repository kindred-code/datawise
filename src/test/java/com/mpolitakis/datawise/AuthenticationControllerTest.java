package com.mpolitakis.datawise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.mpolitakis.datawise.auth.AuthenticationController;
import com.mpolitakis.datawise.auth.AuthenticationRequest;
import com.mpolitakis.datawise.auth.AuthenticationResponse;
import com.mpolitakis.datawise.auth.AuthenticationService;
import com.mpolitakis.datawise.auth.RegisterRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// Import other necessary dependencies and static imports

public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest(); 
        

        when(authenticationService.register(request)).thenReturn(request);

        ResponseEntity<RegisterRequest> result = authenticationController.register(request);

        verify(authenticationService, times(1)).register(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(request, result.getBody());
    }

    @Test
    public void testAuthenticate() throws IllegalArgumentException, JWTCreationException, IOException {
        AuthenticationRequest request = new AuthenticationRequest(); // Create a sample request object
        AuthenticationResponse response = new AuthenticationResponse(); // Create a sample response object

        when(authenticationService.authenticate(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authenticationController.authenticate(request);

        verify(authenticationService, times(1)).authenticate(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}