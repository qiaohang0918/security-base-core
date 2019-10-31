package com.cloud.sercuritybrowser.base;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 15:33
 * @Modified By:
 */
public class BaseCode {
    //验证码
    private String code;
    //过期时间
    private LocalDateTime expireTime;

    public BaseCode(String code, long userableTime) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(userableTime);
    }

    //是否过期
    public boolean isExpired(){
       return LocalDateTime.now().isAfter(this.expireTime);
    }

    public BaseCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
