package com.jen.umsbackend.security;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.jen.umsbackend.users.User;
import com.jen.umsbackend.users.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
// The Algorithm class represents an algorithm to be used in the signing or
// verification process of a token

// AuthenticationManager - processes an authentication request
// Servlet - a class which responds to network requests, usually HTTP requests
// HttpServletRequest - provides request information
// HttpServletResponse - provides HTTP-specific functionality in sending a response

// FilterChain - Object provided by the servlet container to the developer,
// giving a view into the invocation chain of a filtered request for a resource
// Filter - an object that performs filtering tasks on either the request to a resource,
// the response from a resource, or both

// BasicAuthenticationFilter - processes HTTP request's BASIC authorisation headers,
// putting the result into the SecurityContextHolder
// We override it for JWT headers

// SecurityContext - interface defining the minimum security information
// associated with the current thread of execution
// It is stored in the SecurityContextHolder
public class JwtAuthorisationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorisationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    // Each request will go through this when performing authorisation
    // Client sends response including username, password, and jwt token
    // On each subsequent request we send back that token, until it expires
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        // Get the value of the specified request header
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        // If header is null or does not contain BEARER, delegate to Spring and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present build an authentication object with getUsernamePasswordAuthentication()
        // And then add it to the Security Context
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Invoke next filter in the chain
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {   
        
        // Get the header and remove prefix to leave the jwt token
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        if (token != null) {
            // Parse the token, decode the token and verify it
            // Extract subject (username)
            String username = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            // Find user in the DB by token subject (username)
            // Get user's details
            // Create spring auth token, passing in username, password, authorities/roles
            if (username != null) {
                User user = userRepository.findByUsername(username);
                UserPrincipal principal = new UserPrincipal(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
    
}

// DEFINITIONS FROM https://www.codota.com/code/java
