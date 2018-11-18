package org.meng.cloud.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardMonitor {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardMonitor.class);
    }
}
