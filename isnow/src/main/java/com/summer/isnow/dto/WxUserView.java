package com.summer.isnow.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WxUserView {

    private String avatarUrl;
    private String city;
    private String country;
    private String gender;
    private Integer isFollow;
    private String nickName;
    private String province;
    private String userPhone;
    private Integer status;
    private String wxUnionId;
    private String todoOpenId;
    private Date createTime;
    private Date updateTime;
    private String token;
    private Integer userId;
}
