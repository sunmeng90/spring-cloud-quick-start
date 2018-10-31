package org.meng.cloud.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "eureka-uppercase-service", qualifier = "uppercaseServiceFeignClient", fallback = UppercaseServiceFeignClientFallback.class)
public interface UppercaseServiceFeignClient {

    @GetMapping(value = "api/uppercase")
    String uppercase(@RequestParam(name = "name") String name);
}
