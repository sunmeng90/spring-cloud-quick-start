package org.meng.cloud.eureka.client.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/echo")
public class EchoApi {

    @GetMapping
    public ResponseEntity<String> echo(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(name);
    }

}
