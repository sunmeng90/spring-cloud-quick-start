package org.meng.cloud.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController("api/greeting")
public class GreetingApi {

    private RestTemplate restTemplate;
    private String ECHO_URL = "http://localhost:9000/api/echo?name=abc";

    @Autowired
    public GreetingApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<String> greeting(@RequestParam("name") String name) {
        ResponseEntity<String> resp = restTemplate.getForEntity(ECHO_URL, String.class);
        String greetings = resp.getBody();
        return ResponseEntity.ok().body("Greeting, " + greetings);
    }

}
