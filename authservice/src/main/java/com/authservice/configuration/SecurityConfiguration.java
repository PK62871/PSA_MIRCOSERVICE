package com.authservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    //Configuration how to keep
    // 1. url open
    // 2. url to authenticate
    // 3. Authorization



    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {

        //All are open................

//              http.
//                authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll() // Allow all requests without authentication
//                );

        //Welcome, Open hello protected.............
        http.
                authorizeHttpRequests(auth->auth.requestMatchers("api/v1/auth/welcome")
                        .permitAll().anyRequest().authenticated());

        return http.build();
    }
}
