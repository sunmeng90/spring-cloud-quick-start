package org.meng.cloud.app.servcie;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
@Slf4j
@Service
public class UppercaseService {
    private RestTemplate restTemplate;
    private String UPPERCASE_URL = "http://EUREKA-UPPERCASE-SERVICE";//service id is uppercase in eureka registry server

    @Autowired
    public UppercaseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Will create proxy, so avoid inner call
    @HystrixCommand(fallbackMethod = "defaultGetUppercaseName", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public String getUppercaseName(String name) {
        int waitInMilliSeconds = new Random().nextInt(2) * 1000;
        try{
            log.info("waiting for {} milliseconds", waitInMilliSeconds);
            Thread.sleep(waitInMilliSeconds);
        } catch (InterruptedException e) {
            log.error("thread interrupted", e);
        }
        ResponseEntity<String> resp = restTemplate.getForEntity(UPPERCASE_URL + "/api/uppercase?name=" + name, String.class);
        return resp.getBody();
    }

    public String defaultGetUppercaseName(String name){
        log.info("Call fallback method");
        return "defaultGetUppercaseName: "+ StringUtils.upperCase(name);
    }
}
