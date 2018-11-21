package org.meng.cloud.app.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.DummyPing;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class UppercaseRibbonConfiguration {

    @Autowired
    IClientConfig clientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig clientConfig) {
        return new DummyPing();//skip ping
    }

    @Bean
    public IRule ribbonRule(IClientConfig clientConfig) {
        return new RoundRobinRule();
    }
}