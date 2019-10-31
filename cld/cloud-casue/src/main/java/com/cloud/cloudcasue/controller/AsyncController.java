package com.cloud.cloudcasue.controller;

import com.cloud.cloudcasue.queue.DeferredResultAdapter;
import com.cloud.cloudcasue.queue.MockQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/9 22:12
 * @Modified By:
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DeferredResultAdapter deferredResultAdapter;
    @Autowired
    MockQueue mockQueue;

    @RequestMapping("/go/{word}")
    public String go(@PathVariable(value = "word") String word) throws InterruptedException {
        logger.info("主线程开始！");
        Thread.sleep(1000);
        logger.info("主线程结束！");
        return "async go " + word ;
    }


    @RequestMapping("/goCallAble/{word}")
    public Callable<String> goCallAble(@PathVariable(value = "word") String word) throws InterruptedException {
        logger.info("主线程开始！");
        Callable<String> callable = () ->{
            logger.info("副线程开始！");
            Thread.sleep(1000);
            logger.info("副线程开始！");
            return "async go " + word;
        };
        logger.info("主线程结束！");
        return callable ;
    }

    @GetMapping("/goResult/{word}")
    public DeferredResult<Object> goResult(@PathVariable String word){
        logger.info("主线程开始！");
        //装载 参数（枢纽）
        DeferredResult<Object> result = new DeferredResult<Object>();
        deferredResultAdapter.getMap().put(word,result);
        //发送队列消息
        mockQueue.setNewMessage(word);
        logger.info("主线程结束！");
        return result ;
    }


}
