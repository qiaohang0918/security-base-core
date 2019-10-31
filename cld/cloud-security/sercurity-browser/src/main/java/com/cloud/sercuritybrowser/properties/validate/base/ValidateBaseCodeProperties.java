package com.cloud.sercuritybrowser.properties.validate.base;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 16:41
 * @Modified By:
 */
public class ValidateBaseCodeProperties {

    //生成验证码的位数
    int codeLength = 4;

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

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }
}
