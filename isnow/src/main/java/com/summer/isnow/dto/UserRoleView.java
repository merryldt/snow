package com.summer.isnow.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author liudongting
 * @date 2019/7/10 17:11
 */
@Data
public class UserRoleView {

    private Integer id;
    private String name;
    private String description;
    private Integer adminCount;
    private Date createTime;
    private Integer status;
    private Integer sort;
    private String category;

}
