package com.summer.isnow.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WxUserView {

    private Integer id;
    private String icon;
    private String city;
    private String country;
    private String gender;
    private Integer isFollow;
    private String nickName;
    private String province;
    private String userPhone;
    private Integer status;
    private String wxUnionId;
    private String openId;
    private Date createTime;
    private Date updateTime;
    private Integer userId;

    //微信登录，前台传过来的code
    private String code;
    //微信用户解密使用的三个参数
    private String encryptedData;
    private String sessionKey;
    private String iv;

}
