package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
            userService.doAddAdmin(a_user.getUserPhone());
            return ResponseMessage.success();
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
    @GetMapping("/admin/findAll")
    public ResponseMessage findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort) {
        try {
            Page<AdminUser> p = userService.findAdminUser(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)), 9);
            return ResponseMessage.success(p);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 查询所有会员用户
     *
     * @param page
     * @param size
     * @param sort
     * @return
     */
    @GetMapping("/customer/findAllUser")
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
     * 查询一条会员用户
     *
     * @param userId
     * @return
     */
    @PostMapping("/customer/findOneUser")
    public ResponseMessage findOneUser(int userId) {
        try {
            User user = userService.findOneUser(userId);
            return ResponseMessage.getResponseMessage(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
}
