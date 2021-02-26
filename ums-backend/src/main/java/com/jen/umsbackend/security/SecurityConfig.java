package com.jen.umsbackend.security;

import com.jen.umsbackend.users.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserPrincipalDetailsService userPrincipalDetailsService;
    private UserRepository userRepository;

    public SecurityConfig(UserPrincipalDetailsService userPrincipalDetailsService, UserRepository userRepository) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Makes sure any request to the app is authenticated with HTTP Basic authentication
        http
            // Remove csrf and state in session because not needed for token based authentication
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // Add JWT filters - authentication first, then authorisation
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtAuthorisationFilter(authenticationManager(), this.userRepository))
            .authorizeRequests()
            // Configure access rules
            .antMatchers( HttpMethod.POST, "/login").permitAll()
            .antMatchers("/api/v1/profile/*").authenticated()
            .antMatchers("/api/v1/admin/*").hasRole("ADMIN")
            .anyRequest().authenticated();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// RESOURCES USED FOR SPRING SECURTIY

// https://www.youtube.com/channel/UCgj2iw9vh5eX50YvKFZnpbw
// https://github.com/dangeabunea/RomanianCoderExamples/tree/master/SpringBootSecurity
// https://www.baeldung.com/java-config-spring-security
// https://spring.io/guides/topicals/spring-security-architecture
