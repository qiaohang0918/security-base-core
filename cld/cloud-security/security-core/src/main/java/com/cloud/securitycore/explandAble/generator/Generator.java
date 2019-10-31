package com.cloud.securitycore.explandAble.generator;

import com.cloud.sercuritybrowser.base.BaseCode;
import com.cloud.sercuritybrowser.base.ImageCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 17:38
 * @Modified By:
 */
public interface Generator {
    //  generator code
    public BaseCode createCode(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
