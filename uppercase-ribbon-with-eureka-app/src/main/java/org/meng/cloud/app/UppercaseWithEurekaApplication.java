package org.meng.cloud.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UppercaseWithEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UppercaseWithEurekaApplication.class);
    }
}
