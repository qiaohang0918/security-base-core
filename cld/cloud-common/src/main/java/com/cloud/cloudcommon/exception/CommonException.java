package com.cloud.cloudcommon.exception;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/27 14:34
 * @Modified By:
 */
public class CommonException extends Throwable {

    public  CommonException(){
        super();
    }

    public  CommonException(String message){
        super(message);
    }

    public static CommonException getInstance(String message){
        return new CommonException(message);
    }

}
