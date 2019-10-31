package com.cloud.cloudcommon.pojo;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/25 9:55
 * @Modified By:
 */
public class CommonResult<T> {
    public  String code;
    public  String message;
    public T object;

    public static CommonResult success(Object object){
        return new CommonResult("200","操作成功",object);
    }
    public static CommonResult success(){
        return new CommonResult("200","操作成功",null);
    }
    public static CommonResult error(Object object){
        return new CommonResult("400","操作失败",object);
    }
    public static CommonResult error(){
        return new CommonResult("400","操作失败",null);
    }

    public CommonResult(String code, String message, T object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public CommonResult() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
