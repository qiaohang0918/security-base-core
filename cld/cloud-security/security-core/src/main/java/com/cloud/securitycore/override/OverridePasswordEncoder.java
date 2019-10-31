package com.cloud.securitycore.override;

import com.cloud.cloudcommon.md5.MD5;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/4 10:12
 * @Modified By:
 */
//@Component
public class OverridePasswordEncoder implements PasswordEncoder , EmbeddedValueResolverAware {

    // MD5 加密使用的key
    private String key = null;

    @Override
    public String encode(CharSequence charSequence) {
        String encoder = null;
        try {
            encoder = MD5.md5(charSequence.toString(), this.key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoder;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        System.out.println("charSequence:"+charSequence);
        System.out.println("encoderSequence:"+s);
        String encoder = null;
        //根据传入的密钥进行验证
        try {
            encoder = MD5.md5(charSequence.toString(), this.key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean equals = encoder.equals(s);
        System.out.println("自定义加密器(OverridePasswordEncoder)匹配结果："+equals);
        System.out.println("--------------------------------------------------------------------------");
        return equals;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.key = stringValueResolver.resolveStringValue("${qiaohang.md5.key}");
        System.out.println("key:"+this.key);
    }
}
