package com.cloud.securitycore.filter;

import com.cloud.cloudcommon.request_session.SessionConsistance;
import com.cloud.cloudcommon.utils.CommonCheck;
import com.cloud.securitycore.common.CommonAuthenticationFilureHandler;
import com.cloud.securitycore.config.SecurityProperties;
import com.cloud.securitycore.exception.ValidateImageCodeExceprion;
import com.cloud.sercuritybrowser.base.BaseCode;
import com.cloud.sercuritybrowser.base.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 16:13
 * @Modified By:
 */
@Component
public class DefaultSecurityValidateCodeFilter extends OncePerRequestFilter implements CommandLineRunner {

    @Autowired
    CommonAuthenticationFilureHandler commonAuthenticationFilureHandler;

    SessionStrategy sessionStrategy= new HttpSessionSessionStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    private Set<String> validatePath;

    AntPathMatcher pathMatcher = new AntPathMatcher();


    //初始化pathSet
    @Override
    public void run(String... args) throws Exception {
        Set<String> pathSet = new HashSet<>();
        String validatePath = securityProperties.getBrowser().getValidate().getImage().getValidatePath();
        if(!CommonCheck.nullStrings(validatePath)){
            String[] paths = validatePath.split(",");
            for (String path : paths) {
                pathSet.add(path);
            }
        }
        this.validatePath = pathSet;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //判断当前路径是否需要被拦截
        String uri = request.getRequestURI();
        //默认不需要拦截 path imageCode
        boolean flag = false;
        if(!CommonCheck.nullList(validatePath)){
            for (String path : validatePath) {
                //判断是否需要被校验 验证码
                if(pathMatcher.match(path,uri))
                    flag = true;
            }
        }
        if(flag){
            //拦截，校验验证码
            try {
                validateImageCode(request);
            }catch (ValidateImageCodeExceprion e){
                //调用filureHandler
                commonAuthenticationFilureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        //执行后续过滤
        filterChain.doFilter(request,response);
    }

    private void validateImageCode(HttpServletRequest request) {
        //获取session中的imageCode
        Object sessionImageCode = sessionStrategy.getAttribute(new ServletWebRequest(request), SessionConsistance.SESSION_IMAGE_CODE);
        //获取session中的baseCode
        Object sessionBaseCode = sessionStrategy.getAttribute(new ServletWebRequest(request), SessionConsistance.SESSION_BASE_CODE);
        Object attribute = null;
        //默认 imageCode 优先
        if(sessionImageCode==null && sessionBaseCode==null) {
            throw ValidateImageCodeExceprion.throwAble("session中不存在验证码信息，违规登录，刷新重试！");
        }else{
            attribute = sessionImageCode == null ? sessionBaseCode : sessionImageCode;
        }
        BaseCode imageCode = (BaseCode) attribute;
        //从当前轻重中获取验证码
        String validateCode = request.getParameter("validateCode");
        if(CommonCheck.nullStrings(validateCode))
            throw ValidateImageCodeExceprion.throwAble("请输入验证码！");
        if(!imageCode.getCode().equalsIgnoreCase(validateCode))
            throw ValidateImageCodeExceprion.throwAble("验证码错误！");
        if(imageCode.isExpired())
            throw ValidateImageCodeExceprion.throwAble("验证码已过期，刷新页面重新获取！");
    }

}
