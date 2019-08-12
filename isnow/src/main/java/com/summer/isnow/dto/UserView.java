package com.summer.isnow.dto;

import lombok.Data;

import java.util.Date;
/**
 * @author liudongting
 * @date 2019/7/10 17:11
 */
@Data
public class UserView {

    private String userName;
    private String password;
    private String icon;

    private int rows;
    private int page;
}
