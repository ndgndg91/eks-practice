package com.ndgndg91.sellerauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SellerAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellerAuthApplication.class, args);
    }

}
