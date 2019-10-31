package com.cloud.securitycore.explandAble.generator.imagecode;

import com.cloud.cloudcommon.request_session.SessionConsistance;
import com.cloud.securitycore.config.SecurityProperties;
import com.cloud.securitycore.explandAble.generator.Generator;
import com.cloud.sercuritybrowser.base.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.spi.FileSystemProvider;
import java.util.Random;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 17:40
 * @Modified By:
 */
//@Component
public class DefaultImageCodeGenerator  implements CommandLineRunner,ImageCodeGenerator {

    @Autowired
    SecurityProperties securityProperties;

    private String codeChars = null;

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Override
    public void run(String... args) throws Exception {
        codeChars = securityProperties.getBrowser().getValidate().getImage().getCharSet();
    }

    @Override
    public ImageCode createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer validationCode = new StringBuffer();
        // 获得验证码集合的长度
        int charsLength = codeChars.length();
        // *******************设置图形验证码的长和宽（图形的大小）  request  >  defined-Config  >  default-Config
        int width = ServletRequestUtils.getIntParameter(request,"width",securityProperties.getBrowser().getValidate().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request,"height",securityProperties.getBrowser().getValidate().getImage().getHeight());
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获得用于输出文字的Graphics对象
        Graphics g = image.getGraphics();
        Random random = new Random();
        // 随机设置要填充的颜色
        g.setColor(getRandomColor(80, 250));
        // 填充图形背景
        g.fillRect(0, 0, width, height);
        // 设置初始字体
        g.setFont(new Font("Times New Roman", Font.ITALIC, height));
        // 随机设置字体颜色
        g.setColor(getRandomColor(120, 180));
        // 验证码的随机字体
        String[] fontNames = { "Times New Roman", "Book antiqua", "Arial" };
        // *****************随机生成n个验证码   request > definedConfig > defaultConfig
        for (int i = 0; i < securityProperties.getBrowser().getValidate().getImage().getCodeLength(); i++) {
            // 随机设置当前验证码的字符的字体
            g.setFont(new Font(fontNames[random.nextInt(3)], Font.ITALIC, height));
            // 随机获得当前验证码的字符
            char codeChar = codeChars.charAt(random.nextInt(charsLength));
            validationCode.append(codeChar);
            // 随机设置当前验证码字符的颜色
            g.setColor(getRandomColor(10, 100));
            // 在图形上输出验证码字符，x和y都是随机生成的
            g.drawString(String.valueOf(codeChar), 16 * i + random.nextInt(7), height - random.nextInt(6));
        }
        // 关闭Graphics对象
        g.dispose();
        //******************useAbleTime -->  defined-config ---->  default-config
        long userAbleTime = securityProperties.getBrowser().getValidate().getImage().getUserAbleTime();
        ImageCode myInageCode = new ImageCode(image,validationCode.toString(),userAbleTime);
        // 输出验证码
        System.out.println("imageCode:本次生成的验明码："+ validationCode + ", userAbleTime : " + userAbleTime);
        //生成验证码的后置处理
        doGeneratorProcesser(request,response,myInageCode);
        return myInageCode;
    }

    /**
     * 返回一个随机颜色(Color对象)
     * @param minColor
     * @param maxColor
     * @return
     */
    private static Color getRandomColor(int minColor, int maxColor) {
        Random random = new Random();
        // 保存minColor最大不会超过255
        if (minColor > 255)
            minColor = 255;
        // 保存minColor最大不会超过255
        if (maxColor > 255)
            maxColor = 255;
        // 获得红色的随机颜色值
        int red = minColor + random.nextInt(maxColor - minColor);
        // 获得绿色的随机颜色值
        int green = minColor + random.nextInt(maxColor - minColor);
        // 获得蓝色的随机颜色值
        int blue = minColor + random.nextInt(maxColor - minColor);
        return new Color(red, green, blue);
    }
}
