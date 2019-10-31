package com.cloud.sercuritybrowser.base;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 15:33
 * @Modified By:
 */
public class ImageCode extends BaseCode{
    //图像
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, long userableTime) {
        super(code,userableTime);
        this.image = image;
    }

    //是否过期
    public boolean isExpired(){
       return LocalDateTime.now().isAfter(super.getExpireTime());
    }

    public ImageCode() {
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return super.getCode();
    }

    public void setCode(String code) {
        super.setCode(code);
    }

    public LocalDateTime getExpireTime() {
        return super.getExpireTime();
    }

    public void setExpireTime(LocalDateTime expireTime) {
        super.setExpireTime(expireTime);
    }
}
