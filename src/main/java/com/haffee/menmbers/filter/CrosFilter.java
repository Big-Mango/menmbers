package com.haffee.menmbers.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * create by jacktong
 * date 2018/7/28 下午1:28
 * 解决跨域问题
 **/
@Component
public class CrosFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        //String[] whiteList = {"http://localhost:3000", "http://localhost:3001"};
        String[] whiteList = {"http://www.juxiangkezhan.cn", "http://www.heyguy.cn"};
        Set allowedOrigins= new HashSet(Arrays.asList(whiteList));
        String originHeader=((HttpServletRequest) req).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)){
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,key,id,user_type");//user_type 1:个人，2：商家，9：管理员

        }
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
