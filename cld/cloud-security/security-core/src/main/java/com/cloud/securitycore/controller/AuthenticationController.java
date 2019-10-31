package com.cloud.securitycore.controller;

import com.cloud.cloudcommon.pojo.CommonResult;
import com.cloud.securitycore.config.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 9:34
 * @Modified By:
 */
@RestController
public class AuthenticationController {

    @Autowired
    private ObjectMapper objectMapper;

    RequestCache requestCache = new HttpSessionRequestCache();

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    SecurityProperties securityProperties;

    @RequestMapping("/authentication/parseAndForword")
    public CommonResult parseAndForword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取缓存的请求对象
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        //获取引发跳转的请求
        String redirectUrl = savedRequest.getRedirectUrl();
        System.out.println(redirectUrl);
        if(StringUtils.endsWithIgnoreCase(redirectUrl,".html")){
            //走html跳转
            redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
        } else {
            //RestFul请求，返回json
            return CommonResult.error("未登录，请引导用户登录！");
        }
        return null;
    }

    @RequestMapping("/logout.do")
    public CommonResult logout(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        return CommonResult.success("退出成功！");
    }
}
