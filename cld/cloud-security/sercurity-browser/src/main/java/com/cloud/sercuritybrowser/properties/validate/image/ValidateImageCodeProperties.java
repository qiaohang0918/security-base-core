package com.cloud.sercuritybrowser.properties.validate.image;

import org.springframework.web.bind.ServletRequestUtils;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 16:41
 * @Modified By:
 */
public class ValidateImageCodeProperties {

    //验证码长宽
    int width = 100;
    int height = 25;

    //生成验证码的位数
    int codeLength = 4;

    //生成验证码使用的字符集
    String charSet = "0123456789abcdefg";

    //生成验证码的过期时间
    long userAbleTime = 60;

    //需要校验验证码的path
    String validatePath = "/authentication/loginProcessing";

    public String getValidatePath() {
        return validatePath;
    }

    public void setValidatePath(String validatePath) {
        this.validatePath = validatePath;
    }

    public long getUserAbleTime() {
        return userAbleTime;
    }

    public void setUserAbleTime(long userAbleTime) {
        this.userAbleTime = userAbleTime;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }
}
