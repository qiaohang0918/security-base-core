package com.cloud.cloudcasue.queue;

import org.springframework.stereotype.Component;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/9 22:26
 * @Modified By:
 */
//模拟消息队列
@Component
public class MockQueue {

    private String newMessage;
    private String dealedMessage;

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public String getDealedMessage() {
        return dealedMessage;
    }

    public void setDealedMessage(String dealedMessage) {
        this.dealedMessage = dealedMessage;
    }
}
