package com.cloud.securitycore.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 16:18
 * @Modified By:
 */
//图像验证码验证异常
public class ValidateImageCodeExceprion extends AuthenticationException {

    public ValidateImageCodeExceprion(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateImageCodeExceprion(String msg) {
        super(msg);
    }

    public static ValidateImageCodeExceprion throwAble(String msg){
        return  new ValidateImageCodeExceprion(msg);
    }
}
