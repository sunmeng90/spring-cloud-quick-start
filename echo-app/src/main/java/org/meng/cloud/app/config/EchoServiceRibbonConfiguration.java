package org.meng.cloud.app.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class EchoServiceRibbonConfiguration {

    @Autowired
    IClientConfig ribbonClientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig ribbonClientConfig) {
        return new PingUrl(false, "/actuator/info");//set up health, otherwise service can not be found
    }

    @Bean
    public IRule ribbonRule(IClientConfig ribbonClientConfig) {
        return new RoundRobinRule();
    }
}