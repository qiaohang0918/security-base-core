package com.cloud.securitycore.controller;

import com.cloud.cloudcommon.request_session.SessionConsistance;
import com.cloud.securitycore.config.SecurityProperties;
import com.cloud.securitycore.explandAble.generator.Generator;
import com.cloud.securitycore.explandAble.generator.imagecode.ImageCodeGenerator;
import com.cloud.sercuritybrowser.base.BaseCode;
import com.cloud.sercuritybrowser.base.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 15:30
 * @Modified By:
 */
@RestController
public class ValidateImageCodeController {

    @Autowired
    SecurityProperties securityProperties;

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //注入 imageCode  生成器！
    @Autowired
    ImageCodeGenerator imageCodeGenerator;

    @Autowired
    Map<String,Generator> generatorMap;


    @RequestMapping("/code/generator/{type}")
    public void imageCodeGenerator(HttpServletRequest request,
                                   HttpServletResponse response, @PathVariable(required = true) String type) throws IOException {
        //获取验证码生成器
        Generator generator = generatorMap.get(type + "CodeGenerator");
        //创建验证码
        generator.createCode(request,response);
    }

}
