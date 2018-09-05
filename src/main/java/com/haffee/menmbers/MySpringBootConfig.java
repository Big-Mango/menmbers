package com.haffee.menmbers;

import com.haffee.menmbers.interceptor.LoginInterception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * create by jacktong
 * date 2018/7/28 下午2:27
 **/

@Configuration
public class MySpringBootConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterception loginInterception;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterception).
                addPathPatterns("/**").
                excludePathPatterns("/user/admin/login",
                        "/user/customer/login","/base/**");
    }
}
