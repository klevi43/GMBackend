package org.kylecodes.gm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //WebMvcConfigurer.super.addCorsMappings(registry);
                registry.addMapping("/**") // allow all
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:5173")
                        .allowCredentials(true)
                        .allowedHeaders("*");

            }
        };
    }
}
