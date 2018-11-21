package org.meng.cloud.app.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("api/uppercase")
public class UppercaseAppApi {


    private RestTemplate restTemplate;
    private String UPPERCASE_SERVICE_ID = "eureka-uppercase-service";//service id is uppercase in eureka registry server
    private String ZUUL_API_GATEWAY_URL = "http://SERVICE-GATEWAY-WITH-ZUUL";//service id is uppercase in eureka registry server

    @Autowired
    public UppercaseAppApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<String> greeting(@RequestParam("name") String name) {
        ResponseEntity<String> resp = restTemplate.getForEntity(ZUUL_API_GATEWAY_URL + "/" + UPPERCASE_SERVICE_ID + "/api/uppercase?name=" + name, String.class);
        String greetings = resp.getBody();
        return ResponseEntity.ok().body("Greeting, " + greetings + " (via zuul gateway)");
    }
}
