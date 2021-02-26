package com.jen.umsbackend.security;

public class JwtProperties {
    public static final String SECRET = "umstrainingapp";
    public static final long EXPIRATION_TIME  = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer "; // goes in header when jwt is sent back to client
    public static final String HEADER_STRING = "Authorisation";
}

// good practice to keep these in a single class
