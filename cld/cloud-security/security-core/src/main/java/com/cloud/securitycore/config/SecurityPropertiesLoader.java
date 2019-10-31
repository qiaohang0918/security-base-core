package com.cloud.securitycore.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 13:56
 * @Modified By:
 */
@Configuration
@EnableConfigurationProperties(value = {SecurityProperties.class})
public class SecurityPropertiesLoader {


}
