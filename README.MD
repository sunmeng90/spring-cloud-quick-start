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