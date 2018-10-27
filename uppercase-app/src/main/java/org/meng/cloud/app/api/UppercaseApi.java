package org.meng.cloud.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController("api/uppercase")
public class UppercaseApi {

    private RestTemplate restTemplate;
    private String UPPERCASE_URL = "http://eureka-uppercase-service";
//    private String UPPERCASE_URL = "http://eureka-uppercase-service";

    @Autowired
    public UppercaseApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<String> greeting(@RequestParam("name") String name) {
        ResponseEntity<String> resp = restTemplate.getForEntity(UPPERCASE_URL + "/api/uppercase?name=" + name, String.class);
        String greetings = resp.getBody();
        return ResponseEntity.ok().body("Greeting, " + greetings);
    }

}
