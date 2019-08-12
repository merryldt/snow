package com.summer.isnow.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class UserAdminView  {

	private Integer id;
	private String username;
	private String password;
	private String icon;
	private String email;
	private String nickName;
	private String note;
	private Date createTime;
	private Date loginTime;
	private Integer status;
	private Date updateTime;
	private String salt;
	private List<String> roles;

}
