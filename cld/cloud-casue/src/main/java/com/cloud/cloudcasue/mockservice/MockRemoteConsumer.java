package com.cloud.cloudcasue.mockservice;

import com.cloud.cloudcasue.queue.MockQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/9 22:30
 * @Modified By:
 */
//模拟远程消费
@Component
public class MockRemoteConsumer implements CommandLineRunner{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MockQueue mockQueue;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MockRemoteConsumer  startWorking  initing ... ");
        new Thread(()->{
            while (true){
                if(mockQueue.getNewMessage() != null){
                    logger.info("远程服务监听到消息，开始处理：");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("远程服务监处理消息结束");
                    mockQueue.setDealedMessage(mockQueue.getNewMessage());
                    mockQueue.setNewMessage(null);
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
