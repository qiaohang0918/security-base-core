package com.cloud.securitycore.explandAble.generator.basecode;

import com.cloud.cloudcommon.request_session.SessionConsistance;
import com.cloud.securitycore.explandAble.generator.Generator;
import com.cloud.sercuritybrowser.base.BaseCode;
import com.cloud.sercuritybrowser.base.ImageCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/15 14:51
 * @Modified By:
 */
public interface BaseCodeGenerator extends Generator {

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    BaseCode createCode(HttpServletRequest request, HttpServletResponse response);

    default void doGeneratorProcesser(HttpServletRequest request, HttpServletResponse response, BaseCode baseCode){
        //放入session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SessionConsistance.SESSION_BASE_CODE,baseCode);
    }
}
