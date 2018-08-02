package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.repository.AdminUserRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.Md5Utils;
import com.haffee.menmbers.utils.SmsUtils;
import com.haffee.menmbers.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * create by jacktong
 * date 2018/7/15 下午4:44
 **/

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;


    /**
     * 登录 --后台管理
     * @param userPhone
     * @param password
     * @param type 1 商家，9 系统管理员
     * @return
     */
    @Override
    public AdminUser doLoginForAdmin(String userPhone, String password, String type){
        //1.根据用户ID查询，是否存在
        //2.密码加密后判断是否一致
        //3.更新登录状态
        AdminUser a_user = adminUserRepository.findAdminUser(userPhone,type);
        if(null!=a_user&&a_user.getPassword().equals(password)&&a_user.getStatus()==1){
            a_user.setPassword(null);
            String loginKey =UuidUtils.getUUID32();
            a_user.setLoginKey(loginKey);
            Date now = new Date();
            a_user.setLastLoginTime(now);
            adminUserRepository.updateAdminUser(loginKey,now,a_user.getId());
            return a_user;
        }
        return null;
    }


    /**
     * 登录--普通消费者
     * @param userPhone
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public User doLoginForCustomer(String userPhone, String password){

        User c_user = userRepository.findByUserPhone(userPhone);
        if(null!=c_user&&c_user.getPassword().equals(password)&&c_user.getStatus()==1){
            c_user.setPassword(null);
            String loginKey = UuidUtils.getUUID32();
            c_user.setLoginKey(loginKey);
            Date now = new Date();
            userRepository.updateUser(loginKey,now,c_user.getId());
            return c_user;
        }

        return null;
    }

    /**
     * 后台管理注销
     * @param userPhone
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public boolean doLogoutForAdmin(String userPhone, String type){
        AdminUser a_user = adminUserRepository.findAdminUser(userPhone,type);
        if(null!=a_user){
            a_user.setLoginKey("");
            a_user.setLastLoginTime(null);
            adminUserRepository.updateAdminUser("",null,a_user.getId());
        }
        return true;
    }

    /**
     * 消费者注销
     * @param userPhone
     * @return
     * @throws Exception
     */
    @Override
    public boolean doLogoutForCustomer(String userPhone){
        User c_user = userRepository.findByUserPhone(userPhone);
        if(null!=c_user){
            userRepository.updateUser("",null,c_user.getId());
        }
        return true;
    }

    /**
     * 新增系统管理员
     * @param userPhone
     * @return
     * @throws Exception
     */
    @Override
    public int doAddAdmin(String userPhone){
        //0:校验是否存在
        //1.生成密码
        //2.插入AdminUser
        //3.短信通知
        AdminUser a_user = adminUserRepository.findAdminUser(userPhone,"9");
        if(null!=a_user){
            return -1;
        }
        int pre_psw = (int)((Math.random()*9+1)*100000);
        String password = Md5Utils.getMD5(pre_psw+"");
        AdminUser a_user_new = new AdminUser();
        a_user_new.setUserPhone(userPhone);
        a_user.setPassword(password);
        a_user.setType(9);
        a_user.setStatus(1);
        adminUserRepository.save(a_user);
        //发短信
        String sms_content = "聚巷客栈会员系统管理员"+userPhone+"您好：您的账户已经创建成功，登录用户名："+userPhone+",密码："+pre_psw+",请妥善保管！";
        SmsUtils.singleSend(userPhone,sms_content);
        return 0;
    }
}
