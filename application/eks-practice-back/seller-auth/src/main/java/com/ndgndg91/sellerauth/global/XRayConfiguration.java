package com.ndgndg91.sellerauth.global;

import com.amazonaws.xray.AWSXRayRecorder;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.config.DaemonConfiguration;
import com.amazonaws.xray.emitters.Emitter;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.strategy.SegmentNamingStrategy;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XRayConfiguration {

    @Value("${aws.xray.daemon-address}")
    private String daemonAddress;

    @Bean
    public AWSXRayServletFilter awsxRayServletFilter() throws IOException {
        final var daemonConfiguration = new DaemonConfiguration();
        daemonConfiguration.setUDPAddress(daemonAddress);
        final var emitter = Emitter.create(daemonConfiguration);
        AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withEmitter(emitter);
        final AWSXRayRecorder recorder = builder.build();
        return new AWSXRayServletFilter(SegmentNamingStrategy.fixed("SellerApplication"), recorder);
    }
}
