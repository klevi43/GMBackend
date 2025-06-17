package org.kylecodes.gm.configurations;

import org.kylecodes.gm.filters.JwtFilter;
import org.kylecodes.gm.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {
    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> corsConfigurationSource());
        // Configure the CSRF token repository
        CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();

        // Set the Secure flag to true for the XSRF-TOKEN cookie
        csrfTokenRepository.setCookieCustomizer(cookieCustomizer -> cookieCustomizer.secure(true).sameSite("None"));
        http.csrf(csrf ->
                csrf.csrfTokenRepository(csrfTokenRepository)
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));
        http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/register", "/auth/login", "/auth/logout", "/auth/me").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                });
        http.logout(AbstractHttpConfigurer::disable);
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(System.getenv("FRONTEND_URL"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // allow all
                        .allowedMethods("*")
                        .allowedOrigins(System.getenv("FRONTEND_URL"))
                        .allowCredentials(true)
                        .allowedHeaders("*");

            }
        };
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
