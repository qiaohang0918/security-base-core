package com.cloud.securitycore.common;

import com.cloud.cloudcommon.pojo.CommonResult;
import com.cloud.cloudcommon.request_session.RequestConsistance;
import com.cloud.cloudcommon.utils.CommonCheck;
import com.cloud.securitycore.config.SecurityProperties;
import com.cloud.sercuritybrowser.properties.ReturnType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 13:50
 * @Modified By:
 */
@Component
public class CommonAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //根据不同的请求来源类型。选择跳转页面或是，返回json
        String requestHeader = getRequestHeader(request, RequestConsistance.EXPEXT_AUTHENTICATION_RETURN_TYPE);
        //默认true ，走HTML
        boolean flag = true;
        if(requestHeader!=null){
            //走用户指定的响应
            if(requestHeader.toString().equalsIgnoreCase(ReturnType.JSON.toString()))
                flag = false;
            if(!flag) {
                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success("认证成功！")));
            }else {
                //走security框架默认的认证后的逻辑(跳转之前访问的url)
                super.onAuthenticationSuccess(request,response,authentication);
            }
        }else {
            //走配置的响应
            if(securityProperties.getBrowser().getReturnType().equals(ReturnType.JSON)){
                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success("认证成功！")));
            }else {
                //走security框架默认的认证后的逻辑(跳转之前访问的url)
                super.onAuthenticationSuccess(request,response,authentication);
            }
        }
    }


    /**
     * 获取请求头
     * @param request
     * @param header
     * @return
     */
    public String getRequestHeader(HttpServletRequest request,String header){
        String headerValue = request.getHeader(header);
        if(CommonCheck.nullStrings(headerValue))
            return null;
        else
            return headerValue;
    }
}
