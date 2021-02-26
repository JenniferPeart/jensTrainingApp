package com.jen.umsbackend.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

// UsernamePasswordAuthenticationFilter - processes an authentication form submission
// AuthenticationManager - processes an authentication request
// HttpServletRequest - provides request information
// HttpServletResponse - provides HTTP-specific functionality in sending a response

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // Triggerd when we receive POST request to login
    // Username and password e.g. {"username":"jen", "password":"password"} is passed in through the request body
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // Get credentials from client and map them to CredentialsDTO
        CredentialsDTO credentials = null;

        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);
        } catch (IOException e) {
            // e.printStackTrace();
        }

        // If we have credentials, build authentication token
        // Token that spring security will use internally (not the token that we return to user)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());

        // .authenticate() attempts to authenticate the passed Authentication object, 
        // returning a fully populated Authentication object (including granted authorities) if successful
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }

    // If authentication is successful, this method will run
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        
        // Get the user principal
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));

        // Add token in response to client
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
    }
    
}