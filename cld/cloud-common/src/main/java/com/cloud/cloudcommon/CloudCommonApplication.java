package com.cloud.cloudcommon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudCommonApplication.class, args);
    }

}
