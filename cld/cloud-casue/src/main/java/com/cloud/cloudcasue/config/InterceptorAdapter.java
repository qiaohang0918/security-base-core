package com.cloud.cloudcasue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/9 21:31
 * @Modified By:
 */
//@Configuration
public class InterceptorAdapter extends WebMvcConfigurationSupport {


    @Autowired
    CasueImterceptor casueImterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(casueImterceptor).addPathPatterns("/**");
    }



}
