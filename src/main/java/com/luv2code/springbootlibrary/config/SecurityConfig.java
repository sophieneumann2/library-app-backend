package com.luv2code.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Disable Cross Side Request Forgery
        http
                .csrf().disable()

                // Add CORS filters
                .cors().and()
                
                // Protect endpoints at /api/<type>/secure
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/books/secure/**")
                        .authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer()
                .jwt();

        // Add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        // Force a non-empty response body for 401'S to make the response user-friendly
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }
}
