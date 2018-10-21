Issue: 
# Q: Eureka Client application shutdown immediately after start up and registration?
Answer: client is not web application, add dependency below
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```