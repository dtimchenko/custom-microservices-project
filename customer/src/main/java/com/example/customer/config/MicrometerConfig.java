package com.example.customer.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public MeterRegistryCustomizer<PrometheusMeterRegistry> configureMetricsRegistry() {
        return registry -> registry.config().commonTags("appName", appName);
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry){
        return new TimedAspect(meterRegistry);
    }

}
