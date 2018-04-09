package com.linden.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@Configuration
@EnableJdbcHttpSession
@EnableJpaRepositories("com.linden.*")
@ComponentScan(basePackages = { "com.linden.*" })
@EntityScan("com.linden.*")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

}