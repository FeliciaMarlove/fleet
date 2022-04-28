package com.soprasteria.fleet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FleetApplication extends SpringBootServletInitializer {

    private final Environment env;

    public FleetApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(FleetApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String urlClient = env.getProperty("cors.url.client");
                CorsRegistration regClient = registry.addMapping("/api/**");
                regClient.allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins(urlClient);
                String urlExt = env.getProperty("cors.url.external");
                CorsRegistration regExternal = registry.addMapping("/external/**");
                regExternal.allowedMethods("POST").allowedOrigins(urlExt);
            }
        };
    }
}
