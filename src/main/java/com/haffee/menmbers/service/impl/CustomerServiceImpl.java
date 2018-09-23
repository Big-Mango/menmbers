package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.Person;
import com.haffee.menmbers.entity.Shop;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.repository.CardRepository;
import com.haffee.menmbers.repository.PersonRepository;
import com.haffee.menmbers.repository.ShopRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.CustomerService;
import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.HttpClientUtils;
import com.haffee.menmbers.utils.UuidUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * create by jacktong
 * date 2018/9/1 下午1:45
 **/

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private UserRepository userServiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public User checkUserPhone(String phone_no, String openid,String access_token,String refesh_token,String acc_code){
        //1.判断该手机号是否存在会员账户
        //2.如果存在、关联微信openid
        User user_db = userServiceRepository.findByUserPhone(phone_no);
        if(null!=user_db){
            user_db.setWechatId(openid);
            user_db.setAccess_token(access_token);
            String loginKey =UuidUtils.getUUID32();
            Date now = new Date();
            user_db.setLoginKey(loginKey);
            user_db.setLastLoginTime(now);
            //获取微信用户信息
            System.out.println("openid:"+openid+",access_token:"+access_token);
            String result = getWechatUserInfo(openid,access_token);
            System.out.println("获取微信个人信息："+result);
            JSONObject jsStr = JSONObject.fromObject(result);

            String errmsg = jsStr.get("errcode")+"";
            if(null!=errmsg&&errmsg.equals("40001")){
                result = getWechatUserInfo(openid,getAccessToken(acc_code));
                System.out.println("再次获取微信个人信息："+result);
                jsStr = JSONObject.fromObject(result);
            }

            String subscribe = jsStr.get("subscribe")==null?null:jsStr.get("subscribe")+"";
//            if(null!=subscribe&&subscribe.equals("1")){ //关注公众号

                String nickname = jsStr.get("nickname")+"";
                String sex = jsStr.get("sex")==null?"0":jsStr.get("sex")+"";
                String language = jsStr.get("language")+"";
                String city = jsStr.get("city")+"";
                String province = jsStr.get("province")+"";
                String country = jsStr.get("country")+"";
                String headimgurl = jsStr.get("headimgurl")+"";
//                String subscribe_time = jsStr.get("subscribe_time")+"";
//                String remark = jsStr.get("remark")+"";
//                String groupid = jsStr.get("groupid")+"";
//                String tagid_list = jsStr.get("tagid_list")+"";
//                String subscribe_scene = jsStr.get("subscribe_scene")+"";
//                String qr_scene = jsStr.get("qr_scene")+"";
//                String qr_scene_str = jsStr.get("qr_scene_str")+"";
                Person p;
                Optional<Person> o =  personRepository.findById(user_db.getPersonId());
                if(o.isPresent()){
                    p = o.get();
                }else{
                    p = new Person();
                }
                p.setOpenid(openid);
                p.setSubscribe(subscribe);
                p.setNickname(nickname);
                p.setSex(Integer.valueOf(sex));
                p.setLanguage(language);
                p.setCity(city);
                p.setProvince(province);
                p.setCountry(country);
                p.setHeadimgurl(headimgurl);
//                p.setSubscribe_time(subscribe_time);
//                p.setRemark(remark);
//                p.setGroupid(groupid);
//                p.setTagid_list(tagid_list);
//                p.setSubscribe_scene(subscribe_scene);
//                p.setQr_scene(qr_scene);
//                p.setQr_scene_str(qr_scene_str);

                Person person = personRepository.save(p);
                if(!o.isPresent()){
                    user_db.setPersonId(person.getId());
                }
                user_db.setPerson(person);

//            }
            userServiceRepository.save(user_db); //更新用户信息
            List<Card> card_list = cardRepository.findCardByUserId(user_db.getId());
            for (Card card: card_list) {
                Optional<Shop> optional = shopRepository.findById(card.getShopId());
                if(optional.isPresent()){
                    card.setShop(optional.get());
                }
            }
            user_db.setCard_list(card_list);
        }
        return user_db;
    }

    /**
     * 获取微信用户信息
     * @param openid
     * @param access_token
     * @return
     */
    public String getWechatUserInfo(String openid,String access_token){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        String result = HttpClientUtils.get(url);
        return result;
    }

    /**
     * 获取refresh_token
     * @param refresh_token
     * @return
     */
    public String getRefreshToken(String refresh_token){
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+ConfigUtils.getWechat_app_id() +"&grant_type=refresh_token&refresh_token="+refresh_token;
        String result = HttpClientUtils.get(url);
        System.out.println("refresh_token："+refresh_token);
        System.out.println("刷新token结果："+result);
        JSONObject jsStr = JSONObject.fromObject(result);
        String access_token = jsStr.get("access_token")+"";
        return access_token;
    }

    /**
     * 获取accesstoken
     * @param acc_code
     * @return
     */
    public String getAccessToken(String acc_code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ConfigUtils.getWechat_app_id() + "&secret=" + ConfigUtils.getWechat_secret() + "&code=" + acc_code + "&grant_type=authorization_code";
        String result = HttpClientUtils.get(url);
        JSONObject jsStr = JSONObject.fromObject(result);
        String access_token = jsStr.get("access_token")+"";
        return access_token;
    }

}
