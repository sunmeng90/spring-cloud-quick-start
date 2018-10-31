package org.meng.cloud.app.feign;

import org.springframework.stereotype.Component;

@Component
public class UppercaseServiceFeignClientFallback implements UppercaseServiceFeignClient {

    @Override
    public String uppercase(String name) {
        return "Fallback " + name;
    }
}
