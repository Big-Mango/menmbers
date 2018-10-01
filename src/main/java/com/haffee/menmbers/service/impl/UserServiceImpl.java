package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.repository.*;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.Md5Utils;
import com.haffee.menmbers.utils.SmsUtils;
import com.haffee.menmbers.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CardRepository cardRepository;

//    @Autowired
//    private GiftCardRepository giftCardRepository;

    @Autowired
    private CardRechargeRepository cardRechargeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DiscountConfigRepository discountConfigRepository;

    @Autowired
    private CouponsRepository couponsRepository;


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
        if(null!=a_user&&a_user.getPassword().equals(Md5Utils.getMD5(password))&&a_user.getStatus()==1){
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
        a_user_new.setPassword(password);
        a_user_new.setType(9);
        a_user_new.setStatus(1);
        adminUserRepository.save(a_user_new);
        //发短信
        StringBuffer sms_content = new StringBuffer();
        String sms_content_template = ConfigUtils.getAdmin_account_add();
        if(null!=sms_content_template){
            String [] a = sms_content_template.split("&");
            sms_content.append(a[0]+userPhone+a[1]+userPhone+a[2]+pre_psw);
            SmsUtils.singleSend(userPhone,sms_content.toString());
        }
        //String sms_content = "聚巷客栈会员系统管理员"+userPhone+"您好：您的账户已经创建成功，登录用户名："+userPhone+",密码："+pre_psw+",请妥善保管！";

        return 0;
    }

    /**
     * 冻结、解冻会员用户
     * @param id
     * @param status
     * @return
     * @throws Exception
     */
    @Override
    public int changeUserStatus(String id, int status) {
        Optional<User> o = userRepository.findById(Integer.valueOf(id));
        if(o.isPresent()){
            User u = o.get();
            u.setStatus(status);
            userRepository.save(u);
        }
        return 0;
    }

    /**
     * 冻结、解冻管理员用户
     * @param id
     * @param status
     * @return
     * @throws Exception
     */
    @Override
    public int changeAdminUserStatus(String id, int status) {
        Optional<AdminUser> o = adminUserRepository.findById(Integer.valueOf(id));
        if(o.isPresent()){
            AdminUser a = o.get();
            a.setStatus(status);
            adminUserRepository.save(a);
        }
        return 0;
    }

    /**
     * 管理员密码--admin
     * @param id
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public int changePasswordForAdminUser(String id, String password,int type){
        String msg = "";
        Optional<AdminUser> o = adminUserRepository.findById(Integer.valueOf(id));
        if(o.isPresent()){
            AdminUser a = o.get();
            if(type==2){ //店铺用户---自己找回密码
                a.setPassword(Md5Utils.getMD5(password));
                msg = "系统管理用户："+a.getUserPhone()+"您好，您的密码已更新为："+password+",请妥善保管！";
            }
            if(type == 9){ //系统管理员--系统生成密码
                int pre_psw = (int)((Math.random()*9+1)*100000);
                String pws = Md5Utils.getMD5(pre_psw+"");
                a.setPassword(pws);
                msg = "尊敬的商家用户"+a.getUserPhone()+"您好，您的密码已更新为："+pws+",请妥善保管！";
            }
            adminUserRepository.save(a);
            SmsUtils.singleSend(a.getUserPhone(),msg);
        }


        return 0;
    }

    /**
     * 重置密码--会员用户
     * @param id
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public int changePasswordForUser(String id, String password){
        Optional<User> o = userRepository.findById(Integer.valueOf(id));
        if(o.isPresent()){
            User u = o.get();
            u.setPassword(Md5Utils.getMD5(password));
            userRepository.save(u);
            String msg = "尊敬的用户"+u.getUserPhone()+"您好，您的密码已更新为："+password+",请妥善保管！";
            SmsUtils.singleSend(u.getUserPhone(),msg);
        }
        return 0;
    }

    /**
     * 查询单个店铺用户信息
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AdminUser findOneAdminUserForShop(String id){
        Optional<AdminUser> o = adminUserRepository.findById(Integer.valueOf(id));
        if(o.isPresent()){
            AdminUser a_user = o.get();
            Optional<Shop> o_s = shopRepository.findById(Integer.valueOf(a_user.getShopId()));
            if(o_s.isPresent()){
                a_user.setShop(o_s.get());
            }
            return a_user;
        }else{
            return null;
        }
    }

    /**
     * 查询管理用户分页
     * @param pageable
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public Page<AdminUser> findAdminUser(Pageable pageable, int type){
        Page<AdminUser> page = adminUserRepository.findAllByType(type,pageable);
        if(type==2){
            List<AdminUser> list = page.getContent();
            for(AdminUser a_u : list){
                Optional<Shop> o = shopRepository.findById(Integer.valueOf(a_u.getShopId()));
                if(o.isPresent()){
                    a_u.setShop(o.get());
                }
            }
        }
        return page;
    }

    /**
     * 查询会员用户分页
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @Override
    public Page<User> findAllUser(Pageable pageable,int shopId) {
        Page<User> page = userRepository.findAllUser(pageable,shopId);
        if (page != null) {
            List<User> list = page.getContent();
            for (User user : list) {
                //取人员信息
                Optional<Person> optionalPerson = personRepository.findById(user.getPersonId());
                if (optionalPerson.isPresent()) {
                    user.setPerson(optionalPerson.get());
                }
                //取卡信息，注意此处加上了shopId的条件，否则会出现本店铺查询出别的店铺会员信息的问题
                //也就是说固定店铺的每一个用户只有一张对应的会员卡
                Card card = cardRepository.findCardByUserIdShopId(user.getId(),shopId);
                user.setCard(card);
            }
        }
        return page;
    }


    /**
     * 查询会员用户分页
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @Override
    public User findOneUserByUserPhone(Pageable pageable,String userPhone,int shopId) {
        User user = userRepository.findByUserPhoneOrCardNo(userPhone,shopId);
        if (user != null) {
                Optional<Person> optionalPerson = personRepository.findById(user.getPersonId());
                if (optionalPerson.isPresent()) {
                    user.setPerson(optionalPerson.get());
                }
                Card card = cardRepository.findCardByUserIdShopId(user.getId(),shopId);
                if (card!=null) {
                    user.setCard(card);
                }
                //查询优惠券
                List<Coupons> list = couponsRepository.findAllCouponsByUser(user.getId());
                if(list.size()>0){
                    user.setCoupons_list(list);
                }

        }
        return user;
    }

    /**
     * 查询一条会员信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public User findOneUser(int userId){
        Optional<User> user = userRepository.findById(Integer.valueOf(userId));
        if (user.isPresent()) {
            Optional<Person> optionalPerson = personRepository.findById(user.get().getPersonId());
            if (optionalPerson.isPresent()) {
                user.get().setPerson(optionalPerson.get());
            }
        }
        return user.get();
    }
    /**
     * 新增会员信息
     * @param realName
     * @param phoneNo
     * @param cardNo
     * @param cardType
     * @param fee
     * @param ifDiscount
     * @param shopId
     * @return
     * @throws Exception
     */
    public User add(String realName,String phoneNo,String cardNo,int cardType,float fee,String ifDiscount,int shopId) throws Exception{
        //判断该用户是否是该商户下的会员
        User u = userRepository.getUserByUserPhoneShopId(phoneNo,shopId);
        if(u!=null){
            return null;
        }
        int pre_psw = 0;
        float discountFee = 0;
        int discountId = 0 ;
        String discountDesc = "";
        //有折扣信息
        if("1".equals(ifDiscount)){
            //获取最匹配的优惠方案
            List<DiscountConfig> list = discountConfigRepository.findByFullMoney(fee,shopId);
            HashMap<Float,Integer> map = new HashMap();
            //将fee与每一个方案的折扣价格做差，取绝对值(其实正常不取绝对值也是个大于等于0的数)
            for(DiscountConfig discountConfig : list){
                map.put(Math.abs(fee-discountConfig.getFullMoney()),discountConfig.getId());
            }
            if(!map.isEmpty()){
                //此处用Collections.min方法取集合中的最小值
                float rightKey = Collections.min(map.keySet()).floatValue();
                //取最合适方案的id
                int rightId = map.get(rightKey);
                Optional<DiscountConfig> discountConfig = discountConfigRepository.findById(rightId);
                if(discountConfig.isPresent()){
                    discountId = discountConfig.get().getId();
                    discountFee = discountConfig.get().getAddMoney();
                    discountDesc = discountConfig.get().getName();
                }
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(new Date());
        //保存person信息
        Person person = new Person();
        person.setRealName(realName);
        person.setPhoneNo(phoneNo);
        Person responsePerson = personRepository.save(person);
        //保存card信息
        Card card = new Card();
        card.setCardStatus(1);
        card.setCardNo(cardNo);
        card.setCardType(cardType);
        card.setCardCreateTime(createTime);
        card.setShopId(shopId);
        card.setBalance(fee+discountFee);
        Card responseCard = cardRepository.save(card);
        //保存user信息
        User user = new User();
        if (responsePerson!=null&&responseCard!=null){
            //看是否有用户信息
            User ifUser = userRepository.findByUserPhone(phoneNo);
            User responseUser = null;
            if(ifUser!=null){
                //有用户信息就拿过来直接用，此处主要用以解决同一个userPhone在a商户办完会员，还可以在b继续办会员，而不用新建用户，只需再新开一张卡就可以
                responseUser = ifUser;
            }else{
                //没有用户就直接建用户信息
                pre_psw = (int) ((Math.random() * 9 + 1) * 100000);
                String password = Md5Utils.getMD5(pre_psw + "");
                user.setPassword(password);
                user.setUserPhone(responsePerson.getPhoneNo());
                user.setStatus(1);
                user.setPersonId(responsePerson.getId());
                user.setCreateTime(createTime);
                responseUser = userRepository.save(user);
            }
            if(responseUser!=null){
                //回写card表中的userId字段
                responseCard.setUserId(responseUser.getId());
                cardRepository.save(responseCard);
                //保存充值信息
                CardRecharge recharge = new CardRecharge();
                recharge.setCardId(responseCard.getId());
                recharge.setUserPhone(phoneNo);
                recharge.setUserId(responseUser.getId());
                recharge.setShopId(shopId);
                recharge.setFee(fee);
                recharge.setCardNo(cardNo);
                recharge.setCreateTime(createTime);
                recharge.setPaymentTime(createTime);
                recharge.setPaymentStatus(1);
                recharge.setDiscountId(discountId);
                recharge.setDiscountFee(discountFee);
                recharge.setDiscountDesc(discountDesc);
                CardRecharge cardRecharge = cardRechargeRepository.save(recharge);
                if(cardRecharge!=null) {
                    //发送新增会员消息通知
                    StringBuffer sms_content = new StringBuffer();
                    String sms_content_template = ConfigUtils.getPerson_account_add();
                    if (null != sms_content_template) {
                        //拼接短信内容
                        String[] a = sms_content_template.split("&");
                        //phoneNo为用户名pre_psw为密码
                        sms_content.append(a[0] + phoneNo + a[1] + pre_psw);
                        SmsUtils.singleSend(phoneNo, sms_content.toString());
                    }
                }
            }
            return responseUser;
        }else{
            return user;
        }
    }
    /**
     * 修改会员信息
     * @param person
     * @param card
     * @param user
     * @return
     * @throws Exception
     */
    public User update(Person person, Card card, User user) throws Exception{
        //保存person信息
        Person responsePerson = personRepository.save(person);
        //保存card信息
        Card responseCard = cardRepository.save(card);
        //保存user信息
        if (responsePerson!=null&&responseCard!=null){
            user.setUserPhone(responsePerson.getPhoneNo());
            //user.setShopId(responseCard.getShopId());
            User responseUser = userRepository.save(user);
            return responseUser;
        }else{
            return user;
        }
    }

    /**
     * 根据手机号查询超级管理员
     * @param userPhone
     * @return
     * @throws Exception
     */
    @Override
    public AdminUser findAdminUser(String userPhone){
        return adminUserRepository.findAdminUser(userPhone,"9");
    }

    /**
     * 删除管理账户
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public void deleteAdmin(int id){
        adminUserRepository.deleteById(id);
    }

    /**
     * 删除会员
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public void deleteCustomer(int id){
        userRepository.deleteById(id);
    }


}
