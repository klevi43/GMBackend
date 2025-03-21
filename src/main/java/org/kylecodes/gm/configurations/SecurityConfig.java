package org.kylecodes.gm.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
                    auth.anyRequest().authenticated();
                });
        http.httpBasic(Customizer.withDefaults()); // this is an empty lambda
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.csrf((csrf) -> csrf.disable());
        return http.build();
    }
}
