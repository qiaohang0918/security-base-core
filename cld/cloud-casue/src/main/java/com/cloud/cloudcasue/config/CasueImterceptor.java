package com.cloud.cloudcasue.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/9 21:30
 * @Modified By:
 */
//@Component
public class CasueImterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod  handlerMethod = (HandlerMethod) handler;
        Class<?> aClass = handlerMethod.getBean().getClass();
        String name = handlerMethod.getMethod().getName();
        logger.info("当前控制器："+aClass);
        logger.info("当前调度的的方法："+name);
        MethodParameter[] parameters = handlerMethod.getMethodParameters();
        System.out.println("方法参数表列：");
        for (MethodParameter parameter : parameters) {
            System.out.println(parameter.getParameter().getName());
        }
        request.setAttribute("header","qiaohang");
        request.setAttribute("startTime",new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime = (Long) request.getAttribute("startTime");
        logger.info("服务调用结束  post-- 服务耗时："+ (new Date().getTime()-startTime) + " millionSecends!");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("服务调用完成 afterCompletion !");
        if(ex!=null){
            logger.info(ex.getMessage());
        }
    }
}
