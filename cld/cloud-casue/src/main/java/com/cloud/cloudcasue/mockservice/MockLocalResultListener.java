package com.cloud.cloudcasue.mockservice;

import com.cloud.cloudcasue.queue.DeferredResultAdapter;
import com.cloud.cloudcasue.queue.MockQueue;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/9 22:49
 * @Modified By:
 */
//本地处理结果监听
@Component
public class MockLocalResultListener implements CommandLineRunner {

    @Autowired
    MockQueue mockQueue;
    @Autowired
    DeferredResultAdapter deferredResultAdapter;
    private ApplicationContext applicationContext = null;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MockLocalResultListener  initing ...");
        new Thread(()->{
            while (true){
                if(mockQueue.getDealedMessage() != null){
                    String deliveryCrentails = mockQueue.getDealedMessage();
                    deferredResultAdapter.getMap().get(deliveryCrentails).setResult("收到处理结果，返回！");
                    mockQueue.setDealedMessage(null);
                }else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
