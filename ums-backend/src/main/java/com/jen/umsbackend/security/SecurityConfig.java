package com.jen.umsbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserPrincipalDetailsService userPrincipalDetailsService;

    public SecurityConfig(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());

        // In-memory users - not needed now database provider is configured
        
            // .inMemoryAuthentication()
            // .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
            // .and()
            // .withUser("user").password(passwordEncoder().encode("user")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Makes sure any request to the app is authenticated with HTTP Basic authentication
        http
            .authorizeRequests()
            // Order of antMatchers is important
            // Each time a http request comes in, the antMatchers are executed in this order
            .antMatchers("/api/v1/profile/**").authenticated()
            .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
            .and()
            .httpBasic();
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
