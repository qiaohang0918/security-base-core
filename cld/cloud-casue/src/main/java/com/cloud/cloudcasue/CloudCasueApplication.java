package com.cloud.cloudcasue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudCasueApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudCasueApplication.class, args);
    }

}
