package com.ionix.test.backend.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${user.pass}")
    private String pass;

    @Value("${user.username}")
    private String user;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/ionix/v1/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/ionix/v1/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "**").permitAll()
                .antMatchers(HttpMethod.POST, "/ionix/v1/search/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().and().build();
    }

    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.withUsername(user)
                        .password("{noop}"+pass)
                        .authorities("read")
                        .build()
        );
    }

}