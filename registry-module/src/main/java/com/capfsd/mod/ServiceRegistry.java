package com.capfsd.mod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceRegistry {

    public static void main(String[] args) {
        System.out.println("Test2");
        SpringApplication.run(ServiceRegistry.class, args);
    }

}
