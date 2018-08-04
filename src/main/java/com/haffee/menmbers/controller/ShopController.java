package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.Shop;
import com.haffee.menmbers.service.ShopService;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * create by jacktong
 * date 2018/8/4 下午2:44
 **/

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;


    /**
     * 查询单个店铺信息
     *
     * @param user_id
     * @return
     */
    @GetMapping("/findOne")
    public ResponseMessage findShopById(String user_id) {
        try {
            AdminUser a_user = userService.findOneAdminUserForShop(user_id);
            return ResponseMessage.success(a_user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 更新店铺
     *
     * @param shop
     * @param a_user
     * @return
     */
    @PostMapping("/update")
    public ResponseMessage updateShop(@RequestBody Shop shop, @RequestBody AdminUser a_user, int a_user_id, int shop_id) {
        try {
            shop.setId(shop_id);
            a_user.setId(a_user_id);
            shopService.updateShopInfo(a_user, shop);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 新增店铺
     *
     * @param shop
     * @param a_user
     * @return
     */
    @PostMapping("/add")
    public ResponseMessage addShop(@RequestBody Shop shop, @RequestBody AdminUser a_user) {
        try {
            shopService.addShop(a_user, shop);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }

    }

    /**
     * 查询所有店铺
     * @param page
     * @param size
     * @param sort
     * @return
     */
    @GetMapping("/findAll")
    public ResponseMessage findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort){
        try {
            Page<AdminUser> p = userService.findAdminUser(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)), 2);
            return ResponseMessage.success(p);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }



}
