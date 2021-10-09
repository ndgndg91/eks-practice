package com.ndgndg91.sellerauth.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfiguration {

    @Bean
    public JwtResolver jwtResolver(){
        return new JwtResolver("ndgndg91-temp-key", 30, "ndgndg91", 60 * 24);
    }

}
