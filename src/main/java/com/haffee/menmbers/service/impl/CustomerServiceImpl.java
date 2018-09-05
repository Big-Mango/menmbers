package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.Person;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.repository.CardRepository;
import com.haffee.menmbers.repository.PersonRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.CustomerService;
import com.haffee.menmbers.utils.HttpClientUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public User checkUserPhone(String phone_no, String openid,String access_token){
        //1.判断该手机号是否存在会员账户
        //2.如果存在、关联微信openid
        User user_db = userServiceRepository.findByUserPhone(phone_no);
        if(null!=user_db){
            user_db.setWechatId(openid);
            user_db.setAccess_token(access_token);
            //获取微信用户信息
            String result = getWechatUserInfo(openid,access_token);
            JSONObject jsStr = JSONObject.fromObject(result);
            String subscribe = jsStr.get("subscribe")==null?null:jsStr.get("subscribe")+"";
            if(null!=subscribe&&subscribe.equals("1")){ //关注公众号

                String nickname = jsStr.get("nickname")+"";
                String sex = jsStr.get("sex")==null?"0":jsStr.get("sex")+"";
                String language = jsStr.get("language")+"";
                String city = jsStr.get("city")+"";
                String province = jsStr.get("province")+"";
                String country = jsStr.get("country")+"";
                String headimgurl = jsStr.get("headimgurl")+"";
                String subscribe_time = jsStr.get("subscribe_time")+"";
                String remark = jsStr.get("remark")+"";
                String groupid = jsStr.get("groupid")+"";
                String tagid_list = jsStr.get("tagid_list")+"";
                String subscribe_scene = jsStr.get("subscribe_scene")+"";
                String qr_scene = jsStr.get("qr_scene")+"";
                String qr_scene_str = jsStr.get("qr_scene_str")+"";
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
                p.setSubscribe_time(subscribe_time);
                p.setRemark(remark);
                p.setGroupid(groupid);
                p.setTagid_list(tagid_list);
                p.setSubscribe_scene(subscribe_scene);
                p.setQr_scene(qr_scene);
                p.setQr_scene_str(qr_scene_str);

                personRepository.save(p);

            }
            userServiceRepository.save(user_db); //更新用户信息
            List<Card> card_list = cardRepository.findCardByUserId(user_db.getId());
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
        String url = "https;//api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        String result = HttpClientUtils.get(url);
        return result;
    }

}
