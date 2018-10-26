package org.meng.cloud.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GreetingAppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
