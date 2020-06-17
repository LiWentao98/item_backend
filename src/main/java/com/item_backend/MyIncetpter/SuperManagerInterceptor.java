package com.item_backend.MyIncetpter;

import com.item_backend.anno.SuperManagerAnno;
import com.item_backend.config.JwtConfig;
import com.item_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SuperManagerInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    JwtTokenUtil jwtTokenUtil;  //hs256 对称性算法
    @Autowired
    JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            SuperManagerAnno requiredPermission = handlerMethod.getMethod().getAnnotation(SuperManagerAnno.class);
            // 如果方法上的注解为空 则获取类的注解  true 放行
            if (requiredPermission == null) {
                return true;
            }else {
                //处理相关的业务，也就是拦截信息，获取验证信息
                System.out.println("开始核对数据----");
                String token = request.getHeader(jwtConfig.getHeader());
                token = token.substring(jwtConfig.getPrefix().length());

                //如果过期 则拦截
                if (jwtTokenUtil.isTokenExpired(token)){
                    return false;
                }

                boolean b =jwtTokenUtil.checkUserType(request,"超级管理员");
                if (b){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return true;
    }
}
