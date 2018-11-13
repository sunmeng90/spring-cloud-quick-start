package org.meng.cloud.app.api;

import lombok.extern.slf4j.Slf4j;
import org.meng.cloud.app.servcie.UppercaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("api/uppercase")
public class UppercaseAppApi {

    private UppercaseService uppercaseService;

    @Autowired
    public UppercaseAppApi(UppercaseService uppercaseService) {
        this.uppercaseService = uppercaseService;
    }

    @GetMapping
    public ResponseEntity<String> greeting(@RequestParam("name") String name) {
        String greetings = uppercaseService.getUppercaseName(name);
        return ResponseEntity.ok().body("Greeting, " + greetings);
    }


}
