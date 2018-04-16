package com.linden.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@EnableAutoConfiguration
@Configuration
@EnableJdbcHttpSession
@EnableJpaRepositories("com.linden.*")
@ComponentScan(basePackages = {"com.linden.*"})
@EntityScan("com.linden.*")
@EnableTransactionManagement
@PropertySource({
        "classpath:application.properties",
        "classpath:linden.properties"
})
public class ApplicationConfig {

    @Value("${security.hash.strength:-1}")
    private int hashStrength;

    @Value("${security.random.algorithm}")
    private String randomAlgorithm;

    private SecureRandom getNativeSecureRandom() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (!os.contains("win")) {
                return SecureRandom.getInstance("NativePRNGNonBlocking");
            } else {
                return SecureRandom.getInstance("Windows-PRNG");
            }
        } catch (NoSuchAlgorithmException e) {
            // Revert to default SecureRandom configuration
            return new SecureRandom();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        SecureRandom secureRandom = null;
        try {
            if (randomAlgorithm != null) {
                secureRandom = SecureRandom.getInstance(randomAlgorithm);
            }
        } catch (NoSuchAlgorithmException e) {
            secureRandom = getNativeSecureRandom();
        }
        BCryptPasswordEncoder passwordEncoder;
        try {
            passwordEncoder = new BCryptPasswordEncoder(hashStrength, secureRandom);
        } catch (IllegalArgumentException e) {
            // Revert to default configuration with custom SecureRandom
            passwordEncoder = new BCryptPasswordEncoder(-1, secureRandom);
        }
        return passwordEncoder;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}