package com.cloud.securitycore.sms;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/19 15:49
 * @Modified By:
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    //定制短信验证请求的key
    public static final String SMS_REQUEST_KEY = "phone";
    private String smsParameter = SMS_REQUEST_KEY;
    //限制必须post请求
    private boolean postOnly = true;

    //该过滤器匹配拦截的url，请求方式
    public SmsAuthenticationFilter() {
        super(new AntPathRequestMatcher("/sms/login", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String phone = this.obtainPhone(request);
            if (phone == null) {
                phone = "";
            }
            phone = phone.trim();
            SmsAuthenticationToken authRequest = new SmsAuthenticationToken(phone);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    //获取请求中的 smsParameter 参数
    protected String obtainPhone(HttpServletRequest request) {
        return request.getParameter(this.smsParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.smsParameter = usernameParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return this.smsParameter;
    }
}

