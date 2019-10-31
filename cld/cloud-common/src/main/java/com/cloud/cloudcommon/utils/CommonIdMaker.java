package com.cloud.cloudcommon.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/21 14:42
 * @Modified By:
 */
public class CommonIdMaker {

    public static String genetatorIdentifiedId(){
        StringBuffer buffer = new StringBuffer();
        String ss=null;
        buffer.append(
                CommonDateUtil.dateFormat.format(new Date()).split(" ")[0].replace("-","")
        );
        return buffer.toString();
    }
}
