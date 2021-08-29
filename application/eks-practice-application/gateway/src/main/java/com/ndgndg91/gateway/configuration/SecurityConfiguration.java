package com.ndgndg91.gateway.configuration;

import com.ndgndg91.gateway.JwtResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Bean
    public JwtResolver jwtResolver(){
        return new JwtResolver("ndgndg91-temp-key");
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http.httpBasic()
                .and()
                .csrf().disable()
                .headers().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .formLogin().disable()
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost.com:3000", // local
                "http://*.localhost.com:3000", // local
                "http://192.168.*.*:3000", // local
                "http://localhost:3000", // local
                "http://*.localhost:3000", // local
                "https://testcert.kcp.co.kr", // test
                "https://cert.kcp.co.kr", // prod
                "https://selleree.shop", // prod
                "https://*.selleree.shop", // prod
                "null", // etc
                "https://reedev.shop",
                "https://*.reedev.shop",
                "https://reetest.shop",
                "https://*.reetest.shop"
        ));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(1800L);

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
