package com.cloud.securitycore.explandAble.generator.basecode;

import com.cloud.cloudcommon.request_session.SessionConsistance;
import com.cloud.securitycore.config.SecurityProperties;
import com.cloud.securitycore.explandAble.generator.imagecode.ImageCodeGenerator;
import com.cloud.sercuritybrowser.base.BaseCode;
import com.cloud.sercuritybrowser.base.ImageCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 17:40
 * @Modified By:
 */
//@Component
public class DefaultBaseCodeGenerator implements CommandLineRunner,BaseCodeGenerator {

    @Autowired
    SecurityProperties securityProperties;

    private String codeChars = null;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void run(String... args) throws Exception {
        codeChars = securityProperties.getBrowser().getValidate().getImage().getCharSet();
    }

    @Override
    public BaseCode createCode(HttpServletRequest request, HttpServletResponse response) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getBrowser().getValidate().getBase().getCodeLength());
        long userAbleTime = securityProperties.getBrowser().getValidate().getBase().getUserAbleTime();
        BaseCode baseCode = new BaseCode(code, userAbleTime);
        System.out.println("DefaultBaseCodeGenerator -- createCode --> :" + baseCode.getCode() + " , userAbleTime : " + userAbleTime);
        //生成验证码的后置处理
        doGeneratorProcesser(request,response,baseCode);
        return baseCode;
    }

}
