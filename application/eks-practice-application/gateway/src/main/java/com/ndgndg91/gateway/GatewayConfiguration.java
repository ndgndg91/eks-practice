package com.ndgndg91.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("seller-auth", r -> r.path("/apis/sellers/**").uri("http://localhost:1010"))
                .route("buyer-auth", r -> r.path("/apis/buyers/**").uri("http://localhost:2020"))
                .route("product", r -> r.path("/apis/products/**").uri("http://localhost:3030"))
                .route("order", r -> r.path("/apis/orders/**").uri("http://localhost:4040"))
                .build();
    }
}