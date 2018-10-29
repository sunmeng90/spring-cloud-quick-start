package org.meng.cloud.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/uppercase")
public class UppercaseAppApi {

    private RestTemplate restTemplate;
    private String UPPERCASE_URL = "http://EUREKA-UPPERCASE-SERVICE";//service id is uppercase in eureka registry server

    @Autowired
    public UppercaseAppApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<String> greeting(@RequestParam("name") String name) {
        ResponseEntity<String> resp = restTemplate.getForEntity(UPPERCASE_URL + "/api/uppercase?name=" + name, String.class);
        String greetings = resp.getBody();
        return ResponseEntity.ok().body("Greeting, " + greetings);
    }

    @GetMapping("server/actuator")
    public ResponseEntity<String> serverInfo() {
        ResponseEntity<String> resp = restTemplate.getForEntity(UPPERCASE_URL + "/actuator", String.class);
        String serverInfo = resp.getBody();
        return ResponseEntity.ok().body("Call: " + serverInfo);
    }
}
