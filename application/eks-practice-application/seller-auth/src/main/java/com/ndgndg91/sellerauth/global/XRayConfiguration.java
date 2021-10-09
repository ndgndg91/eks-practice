package com.ndgndg91.sellerauth.global;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XRayConfiguration {

    @Bean
    public AWSXRayServletFilter awsxRayServletFilter(){
        return new AWSXRayServletFilter("SellerApplication");
    }

}
