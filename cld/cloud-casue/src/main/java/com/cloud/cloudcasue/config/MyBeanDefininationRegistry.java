package com.cloud.cloudcasue.config;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/13 9:35
 * @Modified By:
 */
public class MyBeanDefininationRegistry implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        BeanDefinition beanDefinenation = new RootBeanDefinition("com.cloud.cloudcasue.mockservice.MockRemoteConsumer");
        beanDefinenation.setLazyInit(false);
        beanDefinenation.setScope("singleton");
        beanDefinitionRegistry.registerBeanDefinition("mockRemoteConsumer",beanDefinenation);
    }
}
