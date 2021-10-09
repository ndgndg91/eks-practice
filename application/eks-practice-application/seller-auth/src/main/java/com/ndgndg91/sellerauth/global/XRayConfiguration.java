package com.ndgndg91.sellerauth.global;

import com.amazonaws.xray.AWSXRayRecorder;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.config.DaemonConfiguration;
import com.amazonaws.xray.emitters.Emitter;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XRayConfiguration {

    @Bean
    public AWSXRayServletFilter awsxRayServletFilter() throws IOException {
        final var daemonConfiguration = new DaemonConfiguration();
        daemonConfiguration.setUDPAddress("xray-service:2000");
        final var emitter = Emitter.create(daemonConfiguration);
        AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withEmitter(emitter);
        final AWSXRayRecorder recorder = builder.build();
        return new AWSXRayServletFilter(null, recorder);
    }
}
