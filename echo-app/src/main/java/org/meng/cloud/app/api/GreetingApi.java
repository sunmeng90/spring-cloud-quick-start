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
    private String ECHO_URL = "http://eureka-echo-service";

    @Autowired
    public GreetingApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<String> greeting(@RequestParam("name") String name) {
//        log.info("Get echo for {} on ", name, this.ECHO_URL);
        ResponseEntity<String> resp = restTemplate.getForEntity(ECHO_URL + "/api/echo?name=" + name, String.class);
        String greetings = resp.getBody();
        return ResponseEntity.ok().body("Greeting, " + greetings);
    }

}
