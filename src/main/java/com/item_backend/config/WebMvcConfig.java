package com.item_backend.config;

import com.item_backend.MyIncetpter.SuperManagerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    SuperManagerInterceptor superManagerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(superManagerInterceptor)
               .addPathPatterns("/user_type/**");
    }
}
