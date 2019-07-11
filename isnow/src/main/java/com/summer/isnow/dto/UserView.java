package com.summer.isnow.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserView {

    private String userName;
    private String password;
    private String icon;

    private int rows;
    private int page;
}
