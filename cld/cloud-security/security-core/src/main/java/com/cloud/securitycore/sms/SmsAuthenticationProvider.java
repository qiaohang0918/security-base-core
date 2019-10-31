package com.cloud.securitycore.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/19 16:00
 * @Modified By:
 */
public class SmsAuthenticationProvider implements  AuthenticationProvider {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) smsAuthenticationToken.getPrincipal());
        if(userDetails == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //成功获取到用户信息，封装已认证的 SmsAuthenticationToken 并返回！
        SmsAuthenticationToken authenticationToken = new SmsAuthenticationToken(smsAuthenticationToken.getPrincipal(),userDetails.getAuthorities());
        authenticationToken.setDetails(authenticationToken.getDetails());
        return authenticationToken;
    }

    //判断是否支持传入的token的校验规则! （传入的类型是否被 SmsAuthenticationToken 签名）
    @Override
    public boolean supports(Class<?> aClass) {
        return SmsAuthenticationToken.class.isAssignableFrom(aClass);
    }

}
