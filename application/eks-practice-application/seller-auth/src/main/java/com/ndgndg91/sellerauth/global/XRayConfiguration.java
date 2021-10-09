package com.ndgndg91.sellerauth.global;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorder;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.plugins.EC2Plugin;
import com.amazonaws.xray.plugins.EKSPlugin;
import com.amazonaws.xray.strategy.DefaultStreamingStrategy;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XRayConfiguration {

    @PostConstruct
    public void init() {
        AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withPlugin(new EC2Plugin()).withPlugin(new EKSPlugin("eks-workshop-cluster"));
        builder.withStreamingStrategy(new DefaultStreamingStrategy(30));
        AWSXRayRecorder globalRecorder = builder.build();
        AWSXRay.setGlobalRecorder(globalRecorder);
    }

    @Bean
    public AWSXRayServletFilter awsxRayServletFilter(){
        return new AWSXRayServletFilter("SellerApplication");
    }

}
