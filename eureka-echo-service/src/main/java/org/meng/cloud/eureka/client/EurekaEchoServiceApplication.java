package org.meng.cloud.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "org.meng.cloud.eureka.client.api")
@EnableDiscoveryClient
public class EurekaEchoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaEchoServiceApplication.class, args);
    }
}