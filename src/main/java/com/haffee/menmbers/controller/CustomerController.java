package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.CardRecharge;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.service.CardRechargeService;
import com.haffee.menmbers.service.CustomerService;
import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.HttpClientUtils;
import com.haffee.menmbers.utils.OrderNumUtils;
import com.haffee.menmbers.utils.ResponseMessage;
import com.haffee.menmbers.utils.wxpay.Md5Util;
import com.haffee.menmbers.utils.wxpay.WXPayRequest;
import com.haffee.menmbers.utils.wxpay.WXPayUtil;
import com.haffee.menmbers.utils.wxpay.WxPayForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * create by jacktong
 * date 2018/9/1 下午12:58
 **/

@RestController
@RequestMapping("/2c")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CardRechargeService cardRechargeService;

    /**
     * 获取关注公众号用户Token
     * @param acc_code
     * @return
     */
    @PostMapping("/wechat/getToken")
    public ResponseMessage getAccessToken(String acc_code) {
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ConfigUtils.getWechat_app_id() + "&secret=" + ConfigUtils.getWechat_secret() + "&code=" + acc_code + "&grant_type=authorization_code";
            String result = HttpClientUtils.get(url);
            return ResponseMessage.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }


    /**
     * 验证个人手机号码,并绑定微信
     * @param openid
     * @param phone_no
     * @return
     */
    @PostMapping("/wechat/checkPhone")
    public ResponseMessage checkPhone(String phone_no,String openid,String access_token,String refresh_token,String acc_code){
        try {
            User user = customerService.checkUserPhone(phone_no,openid,access_token,refresh_token,acc_code);

            return ResponseMessage.success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 点击支付
     * @param card_no
     * @param money
     * @return
     */
    @PostMapping("/wechat/goPaymen")
    public ResponseMessage goPay(String card_no,float money,String openid){
        /**
         * 1.生成系统支付订单
         * 2.调用微信统一下单
         */
        try {
            Map<String, String> map = new HashMap<String, String>();
            //1
            CardRecharge cr = new CardRecharge();
            cr.setCardNo(card_no);
            cr.setCharge_way(1);
            cr.setFee(money);
            cr.setOrderNo(OrderNumUtils.genOrderNum());
            CardRecharge res_cr = cardRechargeService.add(cr);
            //2.
            if(null!=res_cr){
                String appid = ConfigUtils.getWechat_app_id(); //"wx882b32d466df9071";
                String mch_id = ConfigUtils.getWechat_pay_mch_id(); //"1508830801";
                String nonce_str = UUID.randomUUID().toString().replace("-", "");
                String body = "会员卡充值";
                String out_trade_no = cr.getOrderNo();
                int total_fee = (int)(cr.getFee()*100);
                String spbill_create_ip = "127.0.0.1";
                String notify_url = ConfigUtils.getWechat_notice_url(); //"http://47.92.66.33/heygay/wechat/shoporder/callback";
                String trade_type = "JSAPI"; //公众号支付
                String scene_info = "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://heyguy.cn\",\"wap_name\": \"juxiangkezhan\"}";
                String key = ConfigUtils.getWechat_pay_key(); //"Vl91XJX9WgUayxnL1miDy4eVDX94flH6";

                StringBuffer buffer = new StringBuffer();
                buffer.append("appid=" + appid + "&");
                buffer.append("body=" + body + "&");
                buffer.append("mch_id=" + mch_id + "&");
                buffer.append("nonce_str=" + nonce_str + "&");
                buffer.append("notify_url=" + notify_url + "&");
                buffer.append("openid="+ openid + "&");
                buffer.append("out_trade_no=" + out_trade_no + "&");
                buffer.append("scene_info="+scene_info+"&");
                buffer.append("spbill_create_ip=" + spbill_create_ip + "&");
                buffer.append("total_fee=" + total_fee + "&");
                buffer.append("trade_type=" + trade_type + "&");
                buffer.append("key="+key);

                String sign = Md5Util.MD5(buffer.toString()).toUpperCase();
                WxPayForm form = new WxPayForm();
                form.setAppid(appid);
                form.setBody(body);
                form.setMch_id(mch_id);
                form.setNonce_str(nonce_str);
                form.setNotify_url(notify_url);
                form.setOpenid(openid);
                form.setOut_trade_no(out_trade_no);
                form.setSpbill_create_ip(spbill_create_ip);
                form.setTotal_fee(total_fee);
                form.setTrade_type(trade_type);
                form.setScene_info(scene_info);
                form.setSign(sign);
                String req_xml = WXPayUtil.mapToXml(form.getForm_map());
                System.out.println("签名串："+req_xml);
                // 3.发送
                String domain = "api.mch.weixin.qq.com";
                String urlSuffix = "/pay/unifiedorder";
                int connectTimeoutMs = 6 * 1000;
                int readTimeoutMs = 8 * 1000;
                WXPayRequest r = new WXPayRequest(null);
                String result = r.requestOnceSimple(domain, urlSuffix, nonce_str, req_xml, connectTimeoutMs, readTimeoutMs,
                        mch_id);
                Map<String, String> result_map = WXPayUtil.xmlToMap(result);
                System.out.println("微信支付结果：" + result);
                String return_code = result_map.get("return_code");
                boolean flag = false;
                if (return_code.equals("SUCCESS")){
                    String result_code = result_map.get("result_code");
                    if (result_code.equals("SUCCESS")) {
                        String prepay_id = result_map.get("prepay_id");
                        map.put("prepay_id", prepay_id);
                        String package_str = "prepay_id="+prepay_id;
                        Date date = new Date();
                        String s = date.getTime()/1000+"";
                        String a = "appId="+appid+"&nonceStr="+nonce_str+"&package="+package_str+"&signType=MD5&timeStamp="+s+"&key="+key;
                        System.out.println("待签名串："+a);
                        String p_sign = Md5Util.MD5(a).toUpperCase();
                        map.put("ts", s);
                        map.put("noncestr", nonce_str);
                        map.put("sign", p_sign);
                        map.put("order_no", out_trade_no);
                        map.put("appid",appid);
                        flag = true;
                    }
                }
                if(!flag){
                    return ResponseMessage.error();
                }
            }

            return ResponseMessage.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 公众号支付回调
     * @param request
     * @param response
     */
    @PostMapping("/wechat/callback")
    public void pay_callback(HttpServletRequest request, HttpServletResponse response){
        try {
            System.out.println("微信支付进入回调");
            InputStream inStream = request.getInputStream();
            int _buffer_size = 1024;
            if(null!=inStream){
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] tempBytes = new byte[_buffer_size];
                int count = -1;
                while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                    outStream.write(tempBytes, 0, count);
                }
                tempBytes = null;
                outStream.flush();
                //将流转换成字符串
                String result = new String(outStream.toByteArray(), "UTF-8");
                Map<String, String> result_map = WXPayUtil.xmlToMap(result);
                String return_code = result_map.get("return_code");
                if(return_code.equals("SUCCESS")){
                    String result_code = result_map.get("result_code");
                    if(result_code.equals("SUCCESS")){
                        String payment = result_map.get("total_fee"); //分
                        String order_num = result_map.get("out_trade_no");
                        String payment_time = result_map.get("time_end");
                        CardRecharge cr_db = cardRechargeService.findOneByOrderNo(order_num);
                        if(null!=cr_db){
                            cr_db.setPaymentStatus(1);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                            cr_db.setPaymentTime(sdf.format(payment_time)+"");
                            cardRechargeService.save(cr_db);

                            String return_str = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                            response.getWriter().write(return_str);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
