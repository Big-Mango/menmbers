package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.Shop;
import com.haffee.menmbers.repository.AdminUserRepository;
import com.haffee.menmbers.service.ShopService;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.HttpClientUtils;
import com.haffee.menmbers.utils.Md5Utils;
import com.haffee.menmbers.utils.ResponseMessage;
import com.haffee.menmbers.utils.UuidUtils;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private AdminUserRepository adminUserRepository;


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
    public ResponseMessage updateShop(Shop shop, AdminUser a_user, int a_user_id, int shop_id) {
        try {
            shop.setId(shop_id);
            a_user.setId(a_user_id);
            a_user.setType(2);
            a_user.setShopId(shop_id);
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
    public ResponseMessage addShop( Shop shop,  AdminUser a_user) {
        try {
            AdminUser a_user_db = userService.findAdminUserForShop(a_user.getUserPhone());
            if(null!=a_user_db){
                return ResponseMessage.errorWithMsg("用户手机号码已存在");
            }
            List<Shop> shop_list = shopService.findByName(shop.getShopName());
            if(shop_list.size()>0){
                return ResponseMessage.errorWithMsg("店铺名称已存在");
            }
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
     * @return
     */
    @PostMapping("/findAll")
    public ResponseMessage findAll(int page, int size){
        try {
            if(page>0){
                page = page -1;
            }
            if(size==0){
                size = 10;
            }
            Page<AdminUser> p = userService.findAdminUser(PageRequest.of(page, size), 2);
            return ResponseMessage.success(p);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 删除
     * @param a_user_id
     * @param shop_id
     * @return
     */
    @PostMapping("/delete")
    public ResponseMessage delete(int a_user_id,int shop_id){
        try {
            shopService.deleteShop(shop_id,a_user_id);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 供外部查询使用
     * @param phone_or_name
     * @return
     */
    @GetMapping("/findByPhoneName")
    public ResponseMessage findAllByPhoneOrName(String phone_or_name){
        try {
            List<AdminUser> list = shopService.findAllShop(phone_or_name);
            return ResponseMessage.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 联合登录
     * @param vip_shop_id
     * @param meixinag_shop_id
     * @param meixiang_login_key
     * @return
     */
    @PostMapping("/combineLogin")
    public ResponseMessage combineLogin(String vip_shop_id,String meixinag_shop_id,String meixiang_login_key){
        String result = HttpClientUtils.get("http://47.92.66.33/heygay/userservice/checkLogin?login_key="+meixiang_login_key+"&id="+meixinag_shop_id);
        JSONObject j = JSONObject.fromObject(result);
        String r = j.get("result")+"";
        if(r.equals("success")){
            AdminUser au = shopService.combineLogin(vip_shop_id);
            if(null!=au){
                return ResponseMessage.success(au);
            }else{
                return ResponseMessage.error();
            }
        }else{
            return ResponseMessage.error();
        }
    }





}
