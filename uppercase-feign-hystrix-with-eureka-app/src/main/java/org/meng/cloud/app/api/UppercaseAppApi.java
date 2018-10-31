package org.meng.cloud.app.api;

import org.meng.cloud.app.feign.UppercaseServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/uppercase")
public class UppercaseAppApi {

    @Autowired
    @Qualifier("uppercaseServiceFeignClient")
    private UppercaseServiceFeignClient uppercaseServiceFeignClient;

    @GetMapping
    public ResponseEntity<String> greeting(@RequestParam("name") String name) {
        return ResponseEntity.ok().body("Greeting, " + uppercaseServiceFeignClient.uppercase(name));
    }
}
