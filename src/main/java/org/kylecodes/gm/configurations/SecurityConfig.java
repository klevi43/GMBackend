package org.kylecodes.gm.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /*
        disable csrf
        then make the rest api stateless
        rest apis don't need to track state
         */
        /*
        secure by default
        Spring boot autoconfigures a filter to secure the application
        return http.authorizeHttpRequests(authorizeHttp -> {
        authorizeHttp.requestMatchers("/").permitAll(); any request to this url endpoint is fine
        authorizeHttp.anyRequest().authenticated(); any request here much be authenticated
        }
        )
        .oauth2Login(withDefaults()) this is just an empty lambda under the hood
        .build();
         */
        http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/sign-up").permitAll();
                    auth.anyRequest().authenticated();
                });
        http.httpBasic(Customizer.withDefaults()); // this is an empty lambda
//        http.sessionManagement(
//                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
        http.csrf((csrf) -> csrf.disable());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /*
    never store credentials in memory!!!
    encoding vs hashing vs encryption

    encoding: transform data from one form to another (usually more efficient) form. not used for securing data.
    Typically used to compress or stream data. ex: base 64, WAV, Mp3

    hashing: convert data into a hash
        - one way process, not reversible
        - use it to validate integrity of data
    e.g. send request of data and hashed data. The recipient hashes the request's data, and them compares it
    with the request's provided hashed data. if they are the same, then the data is valid and has not
    been manipulated
    ex: bcrypt, scrypt

    encryption: encoding data using a key or password
        - a key or password is necessary to decrypt
        - used to safeguard data
    ex: RSA (Rivest-Shamir-Adleman) public key cryptography system.
    */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
