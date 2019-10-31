package com.cloud.sercuritybrowser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SercurityBrowserApplication implements EmbeddedValueResolverAware {
    private String prot;

    public static void main(String[] args) {
        SpringApplication.run(SercurityBrowserApplication.class, args);
    }

    @GetMapping("/hi")
    public String hi(){
        return "hi security-broswer " + prot;
    }


    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        prot = stringValueResolver.resolveStringValue("${server.port}");
    }
}
