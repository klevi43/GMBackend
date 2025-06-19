package org.kylecodes.gm.configurations;

import org.kylecodes.gm.filters.CsrfLoggingFilter;
import org.kylecodes.gm.filters.JwtFilter;
import org.kylecodes.gm.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;
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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;
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
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        System.out.println("Domain name: " + System.getenv("DOMAIN_NAME"));
        csrfTokenRepository.setCookieCustomizer(csrfTokenRepo -> csrfTokenRepo
                .sameSite("None")
                .secure(true)
                .path("/")
                .domain(System.getenv("DOMAIN_NAME")));
        http.csrf((csrf) -> csrf
                .csrfTokenRepository(csrfTokenRepository)
                .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
        );
        http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/register", "/auth/login", "/auth/logout", "/csrf-cookie").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                });
        http.logout(AbstractHttpConfigurer::disable);
//
//        http.addFilterBefore(new CsrfLoggingFilter(), CsrfFilter.class);

        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    final class SpaCsrfTokenRequestHandler implements CsrfTokenRequestHandler {
        private final CsrfTokenRequestHandler plain = new CsrfTokenRequestAttributeHandler();
        private final CsrfTokenRequestHandler xor = new XorCsrfTokenRequestAttributeHandler();

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
            /*
             * Always use XorCsrfTokenRequestAttributeHandler to provide BREACH protection of
             * the CsrfToken when it is rendered in the response body.
             */
            this.xor.handle(request, response, csrfToken);
            /*
             * Render the token value to a cookie by causing the deferred token to be loaded.
             */
            csrfToken.get();
            System.out.println(csrfToken.get());
            response.addHeader("X-CSRF-Debug-Handled", "true");
        }

        @Override
        public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
            String headerValue = request.getHeader(csrfToken.getHeaderName());
            /*
             * If the request contains a request header, use CsrfTokenRequestAttributeHandler
             * to resolve the CsrfToken. This applies when a single-page application includes
             * the header value automatically, which was obtained via a cookie containing the
             * raw CsrfToken.
             *
             * In all other cases (e.g. if the request contains a request parameter), use
             * XorCsrfTokenRequestAttributeHandler to resolve the CsrfToken. This applies
             * when a server-side rendered form includes the _csrf request parameter as a
             * hidden input.
             */
            return (StringUtils.hasText(headerValue) ? this.plain : this.xor).resolveCsrfTokenValue(request, csrfToken);
        }
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
