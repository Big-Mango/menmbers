package com.haffee.menmbers.interceptor;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.repository.AdminUserRepository;
import com.haffee.menmbers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * create by jacktong
 * date 2018/7/28 下午2:10
 **/

@Component
public class LoginInterception implements HandlerInterceptor {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("********************进入权限拦截器******************");
        //待处理
        String login_key = request.getHeader("key");
        String id = request.getHeader("id");
        String user_type = request.getHeader("user_type"); //1:个人，2：商家，9：管理员

        if (StringUtils.isEmpty(login_key) || StringUtils.isEmpty(id) || StringUtils.isEmpty(user_type)) {
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().write("1003");
            } catch (IOException e) {
                response.getWriter().write("1003");
                e.printStackTrace();
            }
            return false;
        } else {
            if (user_type.equals("2") || user_type.equals("9")) {
                Optional<AdminUser> a_user = adminUserRepository.findById(Integer.valueOf(id));
                if (!a_user.isPresent() || a_user.get().getType() != Integer.valueOf(user_type) || !a_user.get().getLoginKey().equals(login_key)) {
                    response.setContentType("text/html;charset=utf-8");
                    try {
                        response.getWriter().write("1003");
                    } catch (IOException e) {
                        response.getWriter().write("1003");
                        e.printStackTrace();
                    }
                    return false;
                } else {
                    Date date = new Date();
                    long ms = date.getTime() - a_user.get().getLastLoginTime().getTime();
                    long min = ms / (1000 * 60);
                    if (min > 30) {
                        response.setContentType("text/html;charset=utf-8");
                        try {
                            response.getWriter().write("1004");
                        } catch (IOException e) {
                            response.getWriter().write("1004");
                            e.printStackTrace();
                        }
                        return false;
                    } else {
                        //更新登录时间
                        adminUserRepository.updateAdminUser(login_key, date, Integer.valueOf(id));
                        return true;
                    }
                }
            } else if (user_type.equals("1")) {
                Optional<User> c_user = userRepository.findById(Integer.valueOf(id));
                if (!c_user.isPresent() || !c_user.get().getLoginKey().equals(login_key)) {
                    response.setContentType("text/html;charset=utf-8");
                    try {
                        response.getWriter().write("1003");
                    } catch (IOException e) {
                        response.getWriter().write("1003");
                        e.printStackTrace();
                    }
                    return false;
                } else {
                    Date date = new Date();
                    long ms = date.getTime() - c_user.get().getLastLoginTime().getTime();
                    long min = ms / (1000 * 60);
                    if (min > 30) {
                        response.setContentType("text/html;charset=utf-8");
                        try {
                            response.getWriter().write("1004");
                        } catch (IOException e) {
                            response.getWriter().write("1004");
                            e.printStackTrace();
                        }
                        return false;
                    } else {
                        //更新登录时间
                        userRepository.updateUser(login_key, date, Integer.valueOf(id));
                        return true;
                    }
                }

            } else {
                response.setContentType("text/html;charset=utf-8");
                try {
                    response.getWriter().write("1003");
                } catch (IOException e) {
                    response.getWriter().write("1003");
                    e.printStackTrace();
                }
                return false;
            }


        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
