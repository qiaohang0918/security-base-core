package com.cloud.cloudeureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 8:50
 * @Modified By:
 */
@Configuration
@EnableWebSecurity
public class SecurityBasicRegisterConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//关闭csrf
        super.configure(http);
    }
}
