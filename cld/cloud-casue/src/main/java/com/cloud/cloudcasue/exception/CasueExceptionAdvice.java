package com.cloud.cloudcasue.exception;

import com.cloud.cloudcommon.pojo.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/9 21:55
 * @Modified By:
 */
@ControllerAdvice
public class CasueExceptionAdvice{

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public CommonResult dealException(Exception e){
        return CommonResult.error(e.getMessage());
    }

}
