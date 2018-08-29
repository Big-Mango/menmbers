package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.Shop;
import com.haffee.menmbers.repository.AdminUserRepository;
import com.haffee.menmbers.repository.ShopRepository;
import com.haffee.menmbers.service.ShopService;
import com.haffee.menmbers.utils.CopyProperties;
import com.haffee.menmbers.utils.Md5Utils;
import com.haffee.menmbers.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Optional<AdminUser> o_a_u = adminUserRepository.findById(Integer.valueOf(a_user.getId()));
        if(o_a_u.isPresent()){
            AdminUser a_u = o_a_u.get();
            CopyProperties.copyPropertiesIgnoreNull(a_user,a_u);
            adminUserRepository.save(a_u);
        }

        //更新店铺信息
        Optional<Shop> o_s = shopRepository.findById(Integer.valueOf(shop.getId()));
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
        String msg = "尊敬的商家用户"+a_user.getUserPhone()+"您好，您的账户已创建成功，密码为："+pws+",请妥善保管！";
        adminUserRepository.save(a_user);
        SmsUtils.singleSend(a_user.getUserPhone(),msg);

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
}
