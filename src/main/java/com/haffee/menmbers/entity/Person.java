package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * create by jacktong
 * date 2018/7/16 下午7:13
 * 会员本人信息
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private String realName;
    private int sex = 0; //0:位置，1：男，2：女
    private String phoneNo;
    private String address;
    private int label; //待定
    private int job; //待定
    /**以下微信相关字段**/
    private String subscribe = "0";//是否关注公众号
    private String openid ;//微信唯一标识
    private String nickname; //昵称
    private String city; //用户所在城市
    private String country; //用户所在国家
    private String province ;//省份
    private String language;//语言
    private String headimgurl;//头像
    private String subscribe_time;//关注时间戳
    private String remark;//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
    private String groupid;//用户所在的分组ID
    private String tagid_list;//用户被打上的标签ID列表
    /**
     * 返回用户关注的渠道来源，
     * ADD_SCENE_SEARCH 公众号搜索，
     * ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，
     * ADD_SCENE_PROFILE_CARD 名片分享，
     * ADD_SCENE_QR_CODE 扫描二维码，
     * ADD_SCENEPROFILE LINK 图文页内名称点击，
     * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，
     * ADD_SCENE_PAID 支付后关注，
     * ADD_SCENE_OTHERS 其他
     */
    private String subscribe_scene;
    private String qr_scene;//二维码扫码场景（开发者自定义）
    private String qr_scene_str;//二维码扫码场景描述（开发者自定义）

}
