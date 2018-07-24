package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.repository.AdminUserRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * create by jacktong
 * date 2018/7/15 下午4:44
 **/

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private AdminUserRepository adminUserRepository;


    /**
     * 登录 --后台管理
     * @param user_id
     * @param password
     * @param type 1 商家，9 系统管理员
     * @return
     */
    @Override
    public AdminUser dologinForAdmin(String user_id, String password, String type) throws Exception{
        //1.根据用户ID查询，是否存在
        //2.密码加密后判断是否一致
        AdminUser a_user = adminUserRepository.findAdminUser(user_id,type);
        if(null!=a_user&&a_user.getPassword().equals(password)){
            a_user.setPassword(null);
            return a_user;
        }
        return null;
    }

    /**
     * 更新用户
     * @param a_user
     * @return
     */
    @Override
    public boolean updateAdminUser(AdminUser a_user) throws Exception{
        adminUserRepository.updateAdminUser(a_user.getLogin_key(),a_user.getLast_login_time(),a_user.getId());
        return true;
    }
}
