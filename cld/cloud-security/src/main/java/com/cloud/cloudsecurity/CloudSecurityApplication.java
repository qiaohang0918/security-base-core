package com.cloud.cloudsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CloudSecurityApplication implements EmbeddedValueResolverAware {

    public static void main(String[] args) {
        SpringApplication.run(CloudSecurityApplication.class, args);
    }

    String port = null;

    @GetMapping("/hi")
    public String hi(){
        return "hi cloud-eureka " + port ;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        port = stringValueResolver.resolveStringValue("${server.port}");
    }
}
