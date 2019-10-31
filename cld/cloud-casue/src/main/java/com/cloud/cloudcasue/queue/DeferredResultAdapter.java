package com.cloud.cloudcasue.queue;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/9 22:34
 * @Modified By:
 */
@Component
public class DeferredResultAdapter{

    Map<String,DeferredResult<Object>> map = new HashMap<>();

    public Map<String, DeferredResult<Object>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<Object>> map) {
        this.map = map;
    }
}
