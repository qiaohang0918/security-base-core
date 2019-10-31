package com.cloud.securitycore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@SpringBootApplication
@RestController
public class SecurityCoreApplication implements EmbeddedValueResolverAware {

    private String prot = null;

    public static void main(String[] args) {
        SpringApplication.run(SecurityCoreApplication.class, args);
    }

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String hi(){
        return "hi security-core " + prot;
    }

    @GetMapping("/needValidateCode")
    public String needValidateCode(){
        return "needValidateCode  SUCCESS!";
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        prot = stringValueResolver.resolveStringValue("${server.port}");
    }
}
