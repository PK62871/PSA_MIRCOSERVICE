package com.authservice.configuration;

import com.authservice.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {


    //Inject CustomUserDetails it has user details.............
    @Autowired
    private CustomUserDetailsService userDetailsService;


    //Configuration how to keep
    // 1. url open
    // 2. url to authenticate
    // 3. Authorization
//    @Bean
//    public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {

        //All are open................

//              http.
//                authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll() // Allow all requests without authentication
//                );

        //Welcome, Open hello protected.............
//        http.
//                authorizeHttpRequests(auth->auth.requestMatchers("api/v1/auth/welcome")
//                        .permitAll().anyRequest().authenticated());
//
//        return http.build();

//        return null;
//    }


    //...............................................................................................................//

    // Add all url in array which need to be open..
    private final String[] allOpenUrl = {
        "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**"
    };


    //Add in HttpSecurity Object and return SecurityFilterChain object with details...........
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{

        httpSecurity
                .authorizeHttpRequests(auth->auth.requestMatchers(allOpenUrl).permitAll()
                        .requestMatchers("/api/v1/welcome/get").hasAnyRole("USER","ADMIN")
                        .requestMatchers("api/v1/welcome/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf().disable().httpBasic();
        return httpSecurity.build();
    }


    //for Password Encode...........
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}


