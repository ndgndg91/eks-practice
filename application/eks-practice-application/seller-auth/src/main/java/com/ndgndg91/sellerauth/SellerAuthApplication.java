package com.ndgndg91.sellerauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SellerAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellerAuthApplication.class, args);
    }

}
