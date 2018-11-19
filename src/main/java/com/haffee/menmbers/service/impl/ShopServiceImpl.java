package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.Shop;
import com.haffee.menmbers.repository.AdminUserRepository;
import com.haffee.menmbers.repository.ShopRepository;
import com.haffee.menmbers.service.ShopService;
import com.haffee.menmbers.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * create by jacktong
 * date 2018/8/4 下午3:10
 **/

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public int updateShopInfo(AdminUser a_user, Shop shop){

        //更新账户
        Optional<AdminUser> o_a_u = adminUserRepository.findById(a_user.getId());
        if(o_a_u.isPresent()){
            AdminUser a_u = o_a_u.get();
            CopyProperties.copyPropertiesIgnoreNull(a_user,a_u);
            adminUserRepository.save(a_u);
        }

        //更新店铺信息
        Optional<Shop> o_s = shopRepository.findById(shop.getId());
        if(o_s.isPresent()){
            Shop s = o_s.get();
            CopyProperties.copyPropertiesIgnoreNull(shop,s);
            shopRepository.save(s);
        }

        return 0;
    }

    /**
     * 新增商户
     * @param a_user
     * @param shop
     * @throws Exception
     */
    @Override
    public void addShop(AdminUser a_user, Shop shop){

        Shop db_shop = shopRepository.save(shop);
        a_user.setShopId(db_shop.getId());
        a_user.setStatus(1);
        a_user.setType(2);
        int pre_psw = (int)((Math.random()*9+1)*100000);
        String pws = Md5Utils.getMD5(pre_psw+"");
        a_user.setPassword(pws);
        adminUserRepository.save(a_user);

        String sms_content_template = ConfigUtils.getShop_account_add();
        if (null != sms_content_template) {
            //拼接短信内容
            StringBuffer sms_content = new StringBuffer();
            String[] a = sms_content_template.split("&");
            sms_content.append(a[0] + a_user.getUserPhone() + a[1]+pre_psw + a[2]);
            SmsUtils.singleSend(a_user.getUserPhone(), sms_content.toString());
        }


    }

    /**
     * 删除店铺
     * @param shop_id
     * @param a_user_id
     * @return
     * @throws Exception
     */
    @Override
    public void deleteShop(int shop_id, int a_user_id){
            Shop s = new Shop();
            s.setId(shop_id);
            shopRepository.delete(s);
            AdminUser a = new AdminUser();
            a.setId(a_user_id);
            adminUserRepository.delete(a);
    }

    /**
     * 查询所有店铺信息
     * @param phone_or_name
     * @return
     */
    @Override
    public List<AdminUser> findAllShop(String phone_or_name) {
        List<AdminUser> list = adminUserRepository.findShopUserByPhoneOrName(phone_or_name);
        for (AdminUser u:list) {
            Optional<Shop> o = shopRepository.findById(u.getShopId());
            if(o.isPresent()){
                u.setShop(o.get());
            }
        }
        return list;
    }

    /**
     * 联合登录
     * @param
     * @return
     */
    @Override
    public AdminUser combineLogin(String vip_shop_id) {
        AdminUser a_user = adminUserRepository.findByShopId(vip_shop_id);
        if(null!=a_user){
            String loginKey =UuidUtils.getUUID32();
            a_user.setLoginKey(loginKey);
            Date now = new Date();
            a_user.setLastLoginTime(now);
            adminUserRepository.updateAdminUser(loginKey,now,a_user.getId());
            a_user.setPassword(null);
            if(a_user.getType()==2){
                Optional<Shop> s = shopRepository.findById(a_user.getShopId());
                if(s.isPresent()){
                    a_user.setShop(s.get());
                }
            }
            return a_user;
        }else{
            return null;
        }
    }


    @Override
    public List<Shop> findByName(String shop_name) {
        return shopRepository.findByName(shop_name);
    }
}
