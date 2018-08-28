package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.Person;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * create by jacktong
 * date 2018/7/15 下午4:28
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 后台管理登录
     *
     * @param user_phone
     * @param password
     * @param type
     * @return
     */
    @PostMapping("/admin/login")
    public ResponseMessage doLoginForA(String user_phone, String password, String type) {
        try {
            AdminUser a_user = userService.doLoginForAdmin(user_phone, password, type);
            if (null != a_user) {
                return ResponseMessage.success(a_user);
            } else {
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 消费者登录
     *
     * @param user_phone
     * @param password
     * @return
     */
    @PostMapping("/customer/login")
    public ResponseMessage doLoginForC(String user_phone, String password) {
        try {
            User c_user = userService.doLoginForCustomer(user_phone, password);
            if (null != c_user) {
                return ResponseMessage.success(c_user);
            } else {
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 后台用户注销
     *
     * @param user_phone
     * @param type
     * @return
     */
    @PostMapping("/admin/logout")
    public ResponseMessage doLogoutForA(String user_phone, String type) {
        try {
            userService.doLogoutForAdmin(user_phone, type);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }

    }

    /**
     * 消费者注销
     *
     * @param user_phone
     * @return
     */
    @PostMapping("/customer/logout")
    public ResponseMessage doLogoutForC(String user_phone) {
        try {
            userService.doLogoutForCustomer(user_phone);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 新增系统管理员
     *
     * @param a_user
     * @return
     */
    @PostMapping("/admin/add")
    public ResponseMessage addAdminUser(AdminUser a_user) {
        try {
            AdminUser a_user_db = userService.findAdminUser(a_user.getUserPhone());
            if(null==a_user_db){
                userService.doAddAdmin(a_user.getUserPhone());
                return ResponseMessage.success();
            }else{
                return ResponseMessage.errorWithMsg("手机号已存在，请重新输入");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }

    }

    /**
     * 冻结、解冻 会员用户
     *
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/customer/changeStatus")
    public ResponseMessage changeUserStatus(String id, int status) {
        try {
            userService.changeUserStatus(id, status);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 冻结、解冻 管理用户
     *
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/admin/changeStatus")
    public ResponseMessage changeAdminUserStatus(String id, int status) {
        try {
            userService.changeAdminUserStatus(id, status);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 修改密码 --admin
     *
     * @param id
     * @param password
     * @param type
     * @return
     */
    @PostMapping("/admin/changePsw")
    public ResponseMessage changePaswordForAdmin(String id, String password, int type) {
        try {
            userService.changePasswordForAdminUser(id, password, type);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 修改密码 -- 会员用户
     *
     * @param id
     * @param password
     * @return
     */
    @PostMapping("/customer/changePsw")
    public ResponseMessage changePasswordForCustomer(String id, String password) {
        try {
            userService.changePasswordForUser(id, password);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 查询所有管理账户
     *
     * @param page
     * @param size
     * @param sort
     * @return
     */
    @PostMapping("/admin/findAll")
    public ResponseMessage findAll(int page, int size, String sort) {
        try {
//            if(StringUtils.isEmpty(sort)){
//                sort = "id";
//            }
            if(page>0){
                page = page -1;
            }
            if(size==0){
                size = 10;
            }
            Page<AdminUser> p = userService.findAdminUser(PageRequest.of(page, size), 9);
            return ResponseMessage.success(p);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 查询所有会员用户
     * @param page
     * @param size
     * @param sort
     * @return
     */
    @PostMapping("/customer/findAllUser")
    public ResponseMessage findAllUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort) {
        try {
            Page<User> pageUser = userService.findAllUser(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
            return ResponseMessage.getResponseMessage(pageUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 查询所有会员用户
     * @param page
     * @param size
     * @param sort
     * @param userPhone
     * @return
     */
    @PostMapping("/customer/findOneByUserPhone")
    public ResponseMessage findOneByUserPhone(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort, String userPhone) {
        try {
            Page<User> pageUser = userService.findOneUserByUserPhone(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)),userPhone);
            return ResponseMessage.getResponseMessage(pageUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 查询一条会员用户
     * @param userId
     * @return
     */
    @PostMapping("/customer/findOneById")
    public ResponseMessage findOneById(int userId) {
        try {
            User user = userService.findOneUser(userId);
            return ResponseMessage.getResponseMessage(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 新增会员用户
     * @param person
     * @param card
     * @return
     */
    @PostMapping("/customer/add")
    public ResponseMessage addUser(@RequestBody Person person, @RequestBody Card card) {
        try {
            User user = userService.add(person,card);
            return ResponseMessage.getResponseMessage(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
    /**
     * 修改会员用户信息
     * @param person
     * @param card
     * @param user
     * @return
     */
    @PostMapping("/customer/update")
    public ResponseMessage updateUser(@RequestBody Person person, @RequestBody Card card,@RequestBody User user) {
        try {
            User responseUser = userService.update(person,card,user);
            return ResponseMessage.getResponseMessage(responseUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 删除管理账户
     * @param id
     * @return
     */
    @PostMapping("/admin/delete")
    public ResponseMessage deleteAdmin(int id){
        try {
            userService.deleteAdmin(id);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

}
