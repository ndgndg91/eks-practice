package com.ndgndg91.sellerauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SellerAuthApplication {

    public static void main(String[] args) {
        log.info("버전 업그레이드 체킹");
        SpringApplication.run(SellerAuthApplication.class, args);
    }

}
