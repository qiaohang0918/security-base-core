package com.cloud.securitycore.explandAble.generator.imagecode;

import com.cloud.cloudcommon.request_session.SessionConsistance;
import com.cloud.securitycore.explandAble.generator.Generator;
import com.cloud.sercuritybrowser.base.BaseCode;
import com.cloud.sercuritybrowser.base.ImageCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/15 14:37
 * @Modified By:
 */
public interface ImageCodeGenerator extends Generator {

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    ImageCode createCode(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @param request
     * @param response
     * @param myInageCode
     * @throws IOException
     */
    default void doGeneratorProcesser(HttpServletRequest request,HttpServletResponse response,ImageCode myInageCode) throws IOException {
        //放入session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SessionConsistance.SESSION_IMAGE_CODE,myInageCode);
        //写入浏览器（写图像，给出JPEG格式，使用响应浏览器的输出流）
        ImageIO.write(myInageCode.getImage(),"JPEG",response.getOutputStream());
    }
}
