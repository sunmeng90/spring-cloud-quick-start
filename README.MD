**Config Server:**  A http service that fetches configurations and provides to client application

**Eureka Server:**  Service registry

**Eureka client:**  micro service

Eureka server and Eureka client can get configuration from config server by specify config server url:
 ```yaml
 spring:
   application:
     name: eureka-echo-service
   profiles:
     active: local
   cloud:
     config:
       uri: http://localhost:8888 #fetch config from config server
 
 ```
or disable fetch configuration from config server and use local configuration.
```yaml
spring:
  application:
    name: eureka-uppercase-service
  cloud:
    config:
      enabled: false #don't fetch config from config server, use local config

```
Eureka server cluster mock config: 
```
127.0.0.1  eurekapeer0
127.0.0.1  eurekapeer1
127.0.0.1  eurekapeer2
```

**Eureka self-preservation:** <font color='red'>EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.</font>
[Server Self Preservation Mode](https://github.com/Netflix/eureka/wiki/Server-Self-Preservation-Mode)

##Client load balancing with ribbon
The consumer need to list all the service instance address:
```yaml
#bootstrap.yml
eureka-uppercase-service:
  ribbon:
    listOfServers: http://localhost:9800,http://localhost:9801,http://localhost:9802
    ReadTimeout: 2000
```

1. Error `No instances available for eureka-echo-service`
The load balance will check health of service by ping, you can set dummy ping to skip or set the specific available health check url, otherwise, the service will not be marked as available.
```java
public class UppercaseRibbonConfiguration {

    @Autowired
    IClientConfig clientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig clientConfig) {
        return new DummyPing();//skip ping
    }

    @Bean
    public IRule ribbonRule(IClientConfig clientConfig) {
        return new RoundRobinRule();
    }
}
//or
public class EchoServiceRibbonConfiguration {

    @Autowired
    IClientConfig ribbonClientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig ribbonClientConfig) {
//        return new PingUrl();//In case the base url doesn't has a response, the service will be marked as not available
        return new PingUrl(false, "/actuator/info");//set up health, otherwise service can not be found
    }

    @Bean
    public IRule ribbonRule(IClientConfig ribbonClientConfig) {
        return new RoundRobinRule();
    }
}
```
##Client load balancing with ribbon and use eureka to get service server list
The consumer need to know the eureka registry server and need discovery client enabled
```yaml
#bootstrap.yaml
eureka:
  client:
    register-with-eureka: false
    service-url:
      defaultZone: http://eurekapeer0:8080/eureka,http://eurekapeer1:8081/eureka,http://eurekapeer2:8082/eureka

```
```java
@SpringBootApplication
@EnableDiscoveryClient
public class UppercaseWithEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UppercaseWithEurekaApplication.class);
    }
}
```

##How to get the service instances info or selected service instance info
```java
@Slf4j
@RestController
@RequestMapping("api/service-instances")
public class ServiceInstanceApi {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/{applicationName}/current")
    public String logUserInstance(@PathVariable String applicationName) {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose(applicationName);
        log.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
        return serviceInstance.toString();
    }
}

```
##the difference of use eureka or not are:  
1. using eureka, the discovery client can get service instances from eureka registry server  
2. without eureka, the application need list all the service instances address  
The ribbon will select one instance using different load balance rule  

##Declarative webclient with feign
1. Feign is a declarative web service client. It makes writing web service clients easier. To use Feign create an interface and annotate it. 
2. Feign use ribbon and eureka under the hood for load balancing. **Spring Cloud integrates Ribbon and Eureka to provide a load balanced http client when using Feign.**

```java
//--------------enable feign client--------------
@SpringBootApplication
@EnableFeignClients //will scan feign client, can specify scan base packages
@EnableDiscoveryClient
public class UppercaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(UppercaseApplication.class);
    }
}
//-----------------feign client------------------
@FeignClient(name = "eureka-uppercase-service", qualifier = "uppercaseServiceFeignClient")
public interface UppercaseServiceFeignClient {

    @GetMapping(value = "api/uppercase")
    String uppercase(@RequestParam(name = "name") String name);
}

//-------------------inject feign client to consumer----------
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

```

Issue: 
# Q: Eureka Client application shutdown immediately after start up and registration?
Answer: client is not web application, add dependency below
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```


https://blog.csdn.net/u012806787/article/details/78865414?utm_source=blogxgwz0